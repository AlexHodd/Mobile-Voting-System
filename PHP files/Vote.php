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
	'success' => false,
	'voted' => false
);

$selectedVoting = $_POST["selectedVoting"];
$selectedOption = $_POST["selectedOption"];
$login = $_POST["login"];

$sql = "SELECT option , votes FROM `".$selectedVoting."`";
$result = $mysqli->query($sql);

$votes = array();

if ($result->num_rows > 0){
	$i = 1;
	while($row = $result->fetch_array()) {
		$votes[$row[0]] = $row[1];
		$string = explode(", ", $row[1]);
		if (in_array($login, $string)){
			$response["voted"] = true;
			$response["voted for"] = $row[0];
		}
		$i+=1;
	}
		
	$response["votes"] = $votes;
	$optionvotes = explode(", ", $votes[$selectedOption]);
	$response["selected option votes"] = $optionvotes;
	
	$votescount = count($optionvotes);
	$response["votescount"] = $votescount;

	if (!$response["voted"]){
		if ($votes[$selectedOption]=="")
			$newvotes = $login;
		else{
			$optionvotes[count($optionvotes)+1] = $login;
			$newvotes = implode(", ", $optionvotes);
		}
		$sql = "UPDATE `".$selectedVoting."` SET votes='$newvotes' WHERE option='$selectedOption'";
		
		if ($mysqli->query($sql) === TRUE) {
			$response["success"] = true;
		} else {
			$response["error"] = $mysqli->error;
		}
	}
}

$result->close();

echo json_encode($response);
$mysqli->close();
?>