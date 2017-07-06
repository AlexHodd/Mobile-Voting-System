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

if(isset($_POST["voting_name"])){
	$voting_name = $_POST["voting_name"];
	
	$sql = "SELECT liveResults, endDate, description FROM voting WHERE voting_name='$voting_name'";
	$result = $mysqli->query($sql);

	if ($result->num_rows > 0){
		while($row = $result->fetch_array()){
		$response["liveResults"] = $row[0];
		$response["endDate"] = $row[1];
		$response["description"] = $row[2];
		}
	}
	
	$today = date("Y-m-d");
	
	$sql = "SELECT option, votes FROM `".$voting_name."`";
	$result = $mysqli->query($sql);

	if ($result->num_rows > 0){
		
		$response["options_count"] = $result->num_rows;
		$response["success"] = true;
		$i =1;
		while($row = $result->fetch_array()) {
			$response[$i] = $row[0];
			
			if ($response["liveResults"]==1 or $today>=$response["endDate"]){
				if($row[1] == "")
					$response[$i+$result->num_rows] = 0;
				else{
					$optionVotes = explode(", ", $row[1]);
					$response[$i+$result->num_rows] = count($optionVotes);
				}
			}			
			$i+=1;
		}
	}
$result->close();
}
else
	$response["empty_voting_name"] = true;

echo json_encode($response);
$mysqli->close();
?>