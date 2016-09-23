<?php
$json = array();
if (isset($_GET["name"]) && isset($_GET["friendname"])) {
	$name = $_GET["name"];
	$friendname = $_GET["friendname"];
	include_once './GCM.php';
	include_once './db_functions.php';
	$db = new DB_Functions();
	$gcm = new GCM();
	$res1 = $db->addFriend($name, $friendname);
	$res2 = $db->addFriend($friendname, $name);
	if($res1 && $res2){
		echo "success";
		$fid = $db->getGcmID($friendname);
		$registratoin_ids = array($fid);
		$type = "acceptrequest";
		$content = $name . " has accepted your friend request.";
		$message = array("type" => $type, "message" => $content, "senderName" => $name);
		$result = $gcm->send_friendAccept($registratoin_ids, $message);
	}else{
		echo "failure";
	}
} else {
	// user details missing
	echo "missing";
}
?>