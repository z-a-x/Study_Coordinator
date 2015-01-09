<?php
$db = new mysqli('localhost', 'root', '', 'study_coordinator');

if($db->connect_errno > 0){
    die('Unable to connect to database [' . $db->connect_error . ']');
}

if(isset($_GET["id"])) {
	$id = $_GET["id"];
	$sql = <<<SQL
	SELECT user_id, user_name, user_last_name, username, user_avatar, email
    FROM `user` 
	WHERE user_id = $id
SQL;
} else if (isset($_GET["search"])) {
	$query = $_GET["search"];
	$sql = <<<SQL
    SELECT user_id, user_name, user_last_name, username, user_avatar, email
	FROM `user` 
	WHERE user_name LIKE '%$query%' OR user_last_name LIKE '%$query%' OR username LIKE '%$query%' 
SQL;
} else if (isset($_GET["group_id"])) {
	$group_id = $_GET["group_id"];
	$sql = <<<SQL
    SELECT user.user_id, user.user_name, user.user_last_name, user.username, user.user_avatar, user.email
	FROM `user` 
	INNER JOIN `user_group` 
	ON user.user_id = user_group.user_id
    WHERE user_group.group_id = $group_id
SQL;
} else {
	$sql = <<<SQL
    SELECT user_id, user_name, user_last_name, username, user_avatar, email
	FROM `user`
SQL;
}

$data = array();
if(!$result = $db->query($sql)){
    die('There was an error running the query [' . $db->error . ']');
}
while($row = $result->fetch_assoc()){
    #echo json_encode($row['username']." ".$row['user_name']);
	$data[] = $row;
	
}
$json = json_encode(array('users' => $data));
echo $json;
$db->close();

?>
