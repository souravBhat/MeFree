<?php
$json = array();
if (isset($_GET["name"]) && isset($_GET["friendname"])) {
	$name = $_GET["name"];
	$friendname = $_GET["friendname"];
	include_once './GCM.php';
	include_once './db_functions.php';
	$db = new DB_Functions();
	$gcm = new GCM();
	$friend_regid = $db->getGcmID($friendname);
	$registatoin_ids = array($friend_regid);
	$type = "friendrequest";
	$content = $name . " has sent you a friend request. Do you want to accept it?";
	$message = array("type" => $type, "message" => $content, "senderName" => $name);
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