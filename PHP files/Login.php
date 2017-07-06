<?php

$mysqli = new mysqli("localhost", "nazwauzytkownika", "haslo", "bazadanych");

$response = array(
	'success' => false,
	'incorrpass' => false,
	'incorruser' => false);
	
/* check connection */
if ($mysqli->connect_errno) {
	$response["conerror"] = true;
	$response["error"] = $mysqli->connect_error;
	echo json_encode($response);
	die();
}

$login = $_POST["login"];
$haslo = $_POST["haslo"];

$sql = "SELECT * FROM user WHERE username = ?";
$stmt = $mysqli->prepare($sql);	
$stmt->bind_param("s", $login);

if(!$stmt->execute())
	$response["error"] = $mysqli->error;

$stmt->store_result();
if ($stmt->num_rows>0){
	
$stmt->bind_result($user_id, $name, $last_name, $pesel, $username, $password, $admin);
	
	while($stmt->fetch()){
		if ($login==$username){
			if (password_verify($haslo, $password)) {			
				$response["success"] = true;
				$response["user_id"] = $user_id;
				$response["name"] = $name;
				$response["nazwisko"] = $last_name;
				$response["pesel"] = $pesel;
				if($admin==1)
					$response["admin"] = true;
				else
					$response["admin"] = false;	
			}
			else $response["incorrpass"] = true;
		}
	}
}else $response["incorruser"] = true;
$stmt->close();
$mysqli->close();
echo json_encode($response);
?>