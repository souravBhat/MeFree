<?php
$json = array();
include_once './GCM.php';
include_once './db_functions.php';
$db = new DB_Functions();
$gcm = new GCM();
$res = $db->returnAllEvents();
if($res){
	$fields = array(
		'eventlist' => $res,
	);
	$result = json_encode($fields);
	echo $result;
}else{
	echo "failed";
}
?>