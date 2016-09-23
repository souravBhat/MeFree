<?php
$json = array();
if (isset($_GET["eventid"])) {
	$eventid = $_GET["eventid"];
	include_once './GCM.php';
	include_once './db_functions.php';
	$db = new DB_Functions();
	$gcm = new GCM();
	$res = $db->deleteEvent($eventid);
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