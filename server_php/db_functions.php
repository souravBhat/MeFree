<?php
class DB_Functions {
private $db;
//put your code here

// constructor
function __construct() {
	include_once './db_connect.php';
	// connecting to database
	$this->db = new DB_Connect();
	$this->db->connect();
}
// destructor
function __destruct() {
}

/**
* Storing new user
* returns user details
*/
public function storeUser($name, $gcm_regid, $pwd) {
	// insert user into database
	$result = mysql_query("INSERT INTO gcm_users VALUES('$name', NOW(), '$gcm_regid', '$pwd')");
	// check for successful store
	if ($result) {
		// get user details
		$id = mysql_insert_id(); // last inserted id
		$result = mysql_query("SELECT * FROM gcm_users WHERE name = '$name'") or die(mysql_error());
		// return user details
		if (mysql_num_rows($result) == 1) {
			return 1;
		} else {
			return 0;
		}
	} else {
		return 2;
	}
}

public function addFriend($name, $friendname) {
	$result = mysql_query("INSERT INTO contacts VALUES ('$name', '$friendname')");
	if ($result) {
		return true;
	} else {
		return false;
	}
}

/**
* Get user by email and password
*/
/*public function getUserByEmail($email) {
	$result = mysql_query("SELECT * FROM gcm_users WHERE email = '$email' LIMIT 1");
	return $result;
}*/

/**
* Getting all users
*/
/*public function getAllUsers() {
	$result = mysql_query("select * FROM gcm_users");
	return $result;
}*/

/**
* Check user is existed or not
*/
public function isUserExisted($name) {
	$result = mysql_query("SELECT * from gcm_users WHERE name = '$name'");
	$no_of_rows = mysql_num_rows($result);
	if ($no_of_rows > 0) {
		// user existed
		return true;
	} else {
		// user not existed
		return false;
	}
}

public function searchUser($name) {
	$result = mysql_query("SELECT * FROM gcm_users WHERE name != '$name' ORDER BY name");
	$no_of_rows = mysql_num_rows($result);
	if ($no_of_rows > 0) {
		$i = 0;
		while ($row = mysql_fetch_assoc($result)){
			$array[$i] = $row['name'];
			$i++;
		}
		return $array;
	} else {
		return false;
	}
}

public function getGcmID($name) {
	$result = mysql_query("SELECT * from gcm_users WHERE name = '$name'");
	$no_of_rows = mysql_num_rows($result);
	if ($no_of_rows == 1) {
		$user_details = mysql_fetch_assoc($result);
		return $user_details['gcm_regid'] ;
	} else {
		// user not existed
		return "";
	}
}

public function getFriendsName($name) {
	$result = mysql_query("SELECT friendname FROM contacts WHERE name = '$name'");
	$no_of_rows = mysql_num_rows($result);
	if ($no_of_rows > 0) {
		$i = 0;
		while ($row = mysql_fetch_assoc($result)){
			$array[$i] = $row['friendname'];
			$i++;
		}
		return $array;
	} else {
		return false;
	}
}

public function returnAllEvents() {
	$result = mysql_query("SELECT * FROM activity ORDER BY 'time'");
	$no_of_rows = mysql_num_rows($result);
	if ($no_of_rows > 0) {
		$i = 0;
		while ($row = mysql_fetch_assoc($result)){
			$arrayId[$i] = $row['id'];
			$arrayName[$i] = $row['name'];
			$arrayTime[$i] = $row['time'];
			$arrayLocation[$i] = $row['location'];
			$i++;
		}
		$array = array('eventIds' => $arrayId, 'eventNames' => $arrayName, 'eventTimes' => $arrayTime, 'eventLocations' => $arrayLocation);
		return $array;
	} else {
		return false;
	}
}

public function newEvent($name, $desc, $time, $location) {
	$result = mysql_query("INSERT INTO activity VALUES (DEFAULT, '$name', '$desc', '$time', '$location')");
	if ($result) {
		return true;
	} else {
		return false;
	}
}

public function returnLatestEventId() {
	$result = mysql_query("SELECT `AUTO_INCREMENT` FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'GCMDemo' AND TABLE_NAME = 'activity'");
	$row = mysql_fetch_assoc($result);
	$latestID = $row['AUTO_INCREMENT'];
	return $latestID;
}

public function addParticipant($activity_id, $name) {
	$result = mysql_query("INSERT INTO activityAssoc VALUES ('$activity_id', '$name')");
	if ($result) {
		return true;
	} else {
		return false;
	}
}

public function deleteEvent($event_id) {
	$op1 = mysql_query("DELETE FROM activity WHERE id = $event_id");
	$op2 = mysql_query("DELETE FROM activityAssoc WHERE event_id = $event_id");
	$result1 = mysql_query("SELECT * FROM activity WHERE id = $event_id");
	if ($result1) {
		$rows1 = mysql_num_rows($result1);
		if ($rows1 > 0) {
			$r1 = false;
		} else { $r1 = true; }
	} else {
		$r1 = true;
	}
	$result2 = mysql_query("SELECT * FROM activityAssoc WHERE id = $event_id");
	if ($result2) {
		$rows2 = mysql_num_rows($result2);
		if ($rows2 > 0) {
			$r2 = false;
		} else { $r2 = true; }
	} else {
		$r2 = true;
	}
	if ($op1 && $op2 && $r1 && $r2) {
		return true;
	} else {
		return false;
	}
}

public function myEvent($name) {
	$result = mysql_query("SELECT A.id AS 'eventid', A.name AS 'eventname', A.time AS 'eventtime', A.location AS 'eventlocation' FROM activity A, activityAssoc R WHERE R.username = '$name' AND A.id = R.event_id");
	$no_of_rows = mysql_num_rows($result);
	if ($no_of_rows > 0) {
		$i = 0;
		while ($row = mysql_fetch_assoc($result)){
			$arrayId[$i] = $row['eventid'];
			$arrayName[$i] = $row['eventname'];
			$arrayTime[$i] = $row['eventtime'];
			$arrayLocation[$i] = $row['eventlocation'];
			$i++;
		}
		$array = array('eventIds' => $arrayId, 'eventNames' => $arrayName, 'eventTimes' => $arrayTime, 'eventLocations' => $arrayLocation);
		return $array;
	} else {
		return false;
	}
}

public function fetchEventDate($eventid) {
	$result = mysql_query("SELECT 'date' FROM activity WHERE id = '$eventid'");
	if ($result) {
		$row = mysql_fetch_assoc($result);
		$eventDate = $row['date'];
		return $eventDate;
	}
}

public function fetchEventTime($eventid) {
	$result = mysql_query("SELECT time FROM activity WHERE id = '$eventid'");
	if ($result) {
		$row = mysql_fetch_assoc($result);
		$eventtime = $row['time'];
		return $eventtime;
	}
}

public function fetchEventLocation($eventid) {
	$result = mysql_query("SELECT location FROM activity WHERE id = '$eventid'");
	if ($result) {
		$row = mysql_fetch_assoc($result);
		$eventlocation = $row['location'];
		return $eventlocation;
	}
}
public function fetchEventDesc($eventid) {
	$result = mysql_query("SELECT name, time, location, description FROM activity WHERE id = '$eventid'");
	$row = mysql_fetch_assoc($result);
	$name = $row['name'];
	$time = $row['time'];
	$location = $row['location'];
	$description = $row['description'];
	$array = array('eventname' => $name, 'date' => $time, 'location' => $location, 'description' => $description);
	return $array;
}

public function fetchEventParticipants($eventid) {
	$result = mysql_query("SELECT username FROM activityAssoc WHERE event_id = '$eventid'");
	$no_of_rows = mysql_num_rows($result);
	if ($no_of_rows > 0) {
		$i = 0;
		while ($row = mysql_fetch_assoc($result)){
			$name[$i] = $row['username'];
			$i++;
		}
		return $name;
	} else {
		return false;
	}
}

public function fetchEventName($eventid) {
	$result = mysql_query("SELECT name FROM activity WHERE id = '$eventid'");
	$row = mysql_fetch_assoc($result);
	$name = $row['name'];
	return $name;
}

public function validateUser($name, $pwd) {
	$result = mysql_query("SELECT name, pwd FROM gcm_users WHERE name = '$name' AND pwd = '$pwd'");
	$no_of_rows = mysql_num_rows($result);
	if ($no_of_rows > 0) {
		return true;
	} else {
		return false;
	}
}
}
?>