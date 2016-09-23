<?php
$json = array();
if (isset($_GET["eventid"]) && isset($_GET["name"])) {
	$eventid = $_GET["eventid"];
	$name = $_GET["name"];
	include_once './GCM.php';
	include_once './db_functions.php';
	$db = new DB_Functions();
	$gcm = new GCM();
	$res = $db->addParticipant($eventid, $name);
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