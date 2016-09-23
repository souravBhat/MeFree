<?php
	$json = array();
	if (isset($_GET["name"])) {
		$name = $_GET["name"];
		include_once './GCM.php';
		include_once './db_functions.php';
		$db = new DB_Functions();
		$gcm = new GCM();
		$res = $db->getFriendsName($name);
		if($res){
			$fields = array(
				'friendlist' => $res,
			);
			$result = json_encode($fields);
			echo $result;
		}else{
			echo "You have not yet had any friend yet.";
		}
	} else {
		// user details missing
		echo "missing";
	}
?>