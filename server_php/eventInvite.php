<?php
$json = array();
if (isset($_GET["name"]) && isset($_GET["friendname"]) && isset($_GET["eventid"])) {
	$name = $_GET["name"];
	$friendname = $_GET["friendname"];
	$eventid = $_GET["eventid"];
	include_once './GCM.php';
	include_once './db_functions.php';
	$db = new DB_Functions();
	$gcm = new GCM();
	$eventname = $db->fetchEventName($eventid);
	if ($eventname == NULL) {
		return "fail";
	}
	$friend_regid = $db->getGcmID($friendname);
	$eventtime = $db->fetchEventTime($eventid);
	$eventlocation = $db->fetchEventLocation($eventid);
	$registatoin_ids = array($friend_regid);
	$type = "eventInvite";
	$content = $name . " has invited you to the event, [" . $eventname . "]. Do you want to accept it?";
	$message = array("type" => $type, "message" => $content, "eventname" => $eventname, "eventid" => $eventid, "eventtime" => $eventtime, "eventlocation" => $eventlocation);
	$result = $gcm->send_friendRequest($registatoin_ids, $message);
	if ($result) {
		echo "success";
	} else {
		echo "fail";
	}
} else {
	echo "missing";
}
?>