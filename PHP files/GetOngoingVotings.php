<?php
$mysqli = new mysqli("localhost", "nazwauzytkownika", "haslo", "bazadanych");

/* check connection */
if ($mysqli->connect_errno) {
	$response["conerror"] = true;
	$response["error"] = $mysqli->connect_error;
	echo json_encode($response);
	die();
}

$response = array(
	'success' => false
);

$sql = "SELECT voting_name FROM voting 
WHERE startDate<=CURDATE() AND endDate>CURDATE() ORDER BY created";
$result = $mysqli->query($sql);

if ($result->num_rows > 0){
	
	$response["votings_count"] = $result->num_rows;
	$response["success"] = true;
	$i =1;
	while($row = $result->fetch_array()) {
		$response[$i] = $row[0];
		$i+=1;
	}
} else
	$response[emptyfield] = true;

$result->close();

echo json_encode($response);
$mysqli->close();
?>


