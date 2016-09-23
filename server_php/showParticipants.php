<?php
$json = array();
if (isset($_GET["event_id"])) {
	$id = $_GET["event_id"];
	include_once './GCM.php';
	include_once './db_functions.php';
	$db = new DB_Functions();
	$gcm = new GCM();
	$res = $db->fetchEventParticipants($id);
	if($res){
		$fields = array(
			'participants' => $res,
		);
		$result = json_encode($fields);
		echo $result;
	}else{
		echo "failed";
	}
} else {
	echo "missing";
}
?>