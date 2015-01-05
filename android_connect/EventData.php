<?php
$db = new mysqli('localhost', 'root', '', 'study_coordinator');

if($db->connect_errno > 0){
    die('Unable to connect to database [' . $db->connect_error . ']');
}

#$group_name = $_POST["group_name"];
$group_id=$_POST["group_id"];;
$sql = <<<SQL
    SELECT * FROM `event` WHERE group_id=$group_id
SQL;


#SELECT groups.group_id, groups.group_name, user.username, user.user_id, user.user_name, user.user_last_name FROM `user` INNER JOIN `user_group`ON user.user_id=user_group.user_id INNER JOIN `groups` ON groups.group_id=user_group.group_id



if(!$result = $db->query($sql)){
    die('There was an error running the query [' . $db->error . ']');
}
while($row = $result->fetch_assoc()){
    #echo json_encode($row['username']." ".$row['user_name']);
	echo json_encode($row);
}

$db->close();

?>
