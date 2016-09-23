<?php
// response json
$json = array();
/**
* Registering a user device
* Store reg id in users table
*/
if (isset($_GET["name"]) && isset($_GET["gcm_regid"]) && isset($_GET["password"])) {
	$name = $_GET["name"];
	$gcm_regid = $_GET["gcm_regid"]; // GCM Registration ID
	$pwd = $_GET["password"];
	// Store user details in db
	if(include_once('./db_functions.php')){
		if(include_once('./GCM.php')){
			$db = new DB_Functions();
			//$gcm = new GCM();
			$res = $db->storeUser($name, $gcm_regid, $pwd);
			switch($res){
				case 1:
					echo "success";
					break;
				case 0:
					echo "fails";
					break;
				case 2:
					$valid = $db->validateUser($name, $pwd);
					if($valid){
						echo "login_success";
					}else{
						echo "login_fail";
					}
					break;
			}
		}else{
			echo "cant include GCM.php";
		}
	}else{
		echo "cant include db_functions.php";
	}
} else {
// user details missing
echo "missing";
}
?>