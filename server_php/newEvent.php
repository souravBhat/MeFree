<?php
$json = array();
if (isset($_GET["event_name"]) && $_GET["username"]) {
	$username = $_GET["username"];
	$event_name = $_GET["event_name"];
	if ($desc = $_GET["description"]) {
		$desc = $_GET["description"];
	} else {
		$desc = "";
	}
	if (isset($_GET["time"])) {
		$time = $_GET["time"];
	} else {
		$time = "";
	}
	if (isset($_GET["location"])) {
		$location = $_GET["location"];
	} else {
		$location = "";
	}
	include_once './GCM.php';
	include_once './db_functions.php';
	$db = new DB_Functions();
	$gcm = new GCM();
	$res1 = $db->newEvent($event_name, $desc, $time, $location);
	if ($res1) {
		$event_id = ($db->returnLatestEventId() - 1);
		$fields = array(
		'SuccessOrFail' => "success",
		'id' => $event_id,
		);
		$result = json_encode($fields);
	}
	$res2 = $db->addParticipant($event_id, $username);
	if ($res1 && $res2) {
		echo $result;
	}else{
		$fields = array(
		'successOrFail' => "fail",
		'id' => null,
		);
		$result = json_encode($fields);
		echo $result;
	}
} else {
	// user details missing
	echo "missing";
}
?>