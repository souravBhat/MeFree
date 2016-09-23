<?php
$json = array();
if (isset($_GET["event_id"])) {
	$id = $_GET["event_id"];
	include_once './GCM.php';
	include_once './db_functions.php';
	$db = new DB_Functions();
	$gcm = new GCM();
	$res = $db->fetchEventDesc($id);
	$result = json_encode($res);
	echo $result;
} else {
	echo "missing";
}
?>