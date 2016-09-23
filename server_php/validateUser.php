<?php
$json = array();
if (isset($_GET["username"]) && isset($_GET["password"])) {
	$name = $_GET["username"];
	$pwd = $_GET["password"];
	include_once './GCM.php';
	include_once './db_functions.php';
	$db = new DB_Functions();
	$gcm = new GCM();
	$res = $db->validateUser($name, $pwd);
	if($res){
		echo "success";
	}else{
		echo "failure";
	}
} else {
	// user details missing
	echo "missing";
}
?>