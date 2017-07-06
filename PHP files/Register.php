<?php

$mysqli = new mysqli("localhost", "nazwauzytkownika", "haslo", "bazadanych");
/* check connection */
if ($mysqli->connect_errno) {
    $response = array(
		'success' => false,
		'conerror' => true,
		'error' => $mysqli->connect_error);
		echo json_encode($response);
		die();
}

$response = array(
	'success' => false,
	'emptyfields' => false);
if (isset($_POST["nazwauzytkownika"])){
	$nazwauzytkownika = $_POST["nazwauzytkownika"];
		
	$sql = "SELECT username FROM user WHERE username = ? ";
	
	$stmt = $mysqli->prepare($sql);
	$stmt->bind_param("s", $nazwauzytkownika);
	$stmt->execute();
				
	if ($stmt->get_result()->num_rows>0){
		$response["fetched"] = true;
		$response["usernameexists"] = true;
	}
	else{
		$response["fetched"] = false;
		$response["success"] = true;
	}
	$stmt->close();
}
else
	$response["emptyfields"] = true;


if ($response["success"]==true){
	if(isset($_POST["imie"])&&($_POST["nazwisko"])&&($_POST["pesel"])&&($_POST["haslo"])){
		$imie = $_POST["imie"];
		$nazwisko = $_POST["nazwisko"];
		$pesel = $_POST["pesel"];
		$haslohashed = password_hash($_POST["haslo"], PASSWORD_DEFAULT);
		
		$sql = "INSERT INTO user (name, last_name, pesel, username, password) VALUES (?, ?, ?, ?, ?)";
		$stmt = $mysqli->prepare($sql);
		$stmt->bind_param("sssss", $imie, $nazwisko, $pesel, $nazwauzytkownika, $haslohashed);
		if($stmt->execute())
			$response["created"] = true;
		else{
			$response["error"] = $stmt->error;
			$response["success"] = false;
		}

		$stmt->close();
	}
	else
		$response["emptyfields"] = true;
}

echo json_encode($response);
$mysqli->close();
?>