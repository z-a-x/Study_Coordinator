<?php
$db = new mysqli('localhost', 'root', '', 'study_coordinator');

if($db->connect_errno > 0){
    die('Unable to connect to database [' . $db->connect_error . ']');
}


// if id is passed return only group with the id
if(isset($_GET["group_id"]) && isset($_GET["user_id"])) {
	$groupId = $_GET["group_id"];
	$userId = $_GET["user_id"];
	$sql = <<<SQL
	INSERT INTO user_group (group_id, user_id)
	VALUES ($groupId, $userId)
SQL;

	if($db->query($sql) === TRUE){
		$data = array();
		$data[] = "OK";
		$json = json_encode(array('groups' => $data));
		echo $json;
	} else {
		echo "Error when inserting row " . $sql . "<br>" . $db->error;
	}

} else {
	echo "Bad request";
}

	
$db->close();

?>
