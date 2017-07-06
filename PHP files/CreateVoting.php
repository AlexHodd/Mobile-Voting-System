<?php
$mysqli = new mysqli("localhost", "nazwauzytkownika", "haslo", "bazadanych");

/* check connection */
if ($mysqli->connect_errno) {
	$response["conerror"] = true;
	$response["error"] = $mysqli->connect_error;
	echo json_encode($response);
	die();
}

$voting_name = $_POST["voting_name"];
$options_count = $_POST["options_count"];
$description = $_POST["description"];
$options = $_POST["options"];
$startDate = $_POST["startDate"];
$endDate = $_POST["endDate"];
$liveResults = $_POST["liveResults"];
if($liveResults)
	$liveResultsInt = 1;
else
	$liveResultsInt = 0;
$insertStartDate = date("Y-m-d", strtotime($startDate));
$insertEndDate = date("Y-m-d", strtotime($endDate));

$response = array(
	'success' => false,
	'inserted' => false
);

/* check if voting exists */
$sql = "SELECT voting_name FROM voting WHERE voting_name = ? ";
$stmt = $mysqli->prepare($sql);
$stmt->bind_param("s", $voting_name);
if (!$stmt->execute()){
	$response["error"] = $mysqli->error;
	echo json_encode($response);
	$mysqli->close();
	die();
}
if ($stmt->get_result()->num_rows>0){
	$response["votingexists"] = true;
	echo json_encode($response);
	$mysqli->close();
	die();
}else $response["votingexists"] = false;

$stmt->close();

if ($response["votingexists"]==false){
	$sql = "INSERT INTO voting (voting_name, options_count, description, liveResults, startDate, endDate) VALUES (?, ?, ?, ?, ?, ?)";
	$stmt = $mysqli->prepare($sql);
	$stmt->bind_param("sisiss", $voting_name, $options_count, $description, $liveResultsInt, $insertStartDate, $insertEndDate);
	if(!$stmt->execute())
		$response["error"] = $stmt->error;
	else
		$response["inserted"] = true;

	$stmt->close();
}

if ($response["votingexists"]==false){
	
$sql = "CREATE TABLE `".$voting_name."` (
option_id TINYINT(255) UNSIGNED AUTO_INCREMENT PRIMARY KEY, 
option VARCHAR(50) NOT NULL UNIQUE,
votes MEDIUMTEXT NOT NULL
)";

if($mysqli->query($sql)){
	$response["success"] = true;
	$response["created"] = true;
}
else
	$response["error_creating"] = $mysqli->error;
}

$sql = "INSERT INTO `".$voting_name."` (option ) VALUES (?)";
$stmt = $mysqli->prepare($sql);
$stmt->bind_param("s", $option);

$optionsArray = explode(", ", $options);

for ($x = 0; $x < $options_count; $x++) {
    $option = $optionsArray[$x];
	if(!$stmt->execute())
		$response["error "+$x] = $stmt->error;
}

$stmt->close();

echo json_encode($response);
$mysqli->close();
?>