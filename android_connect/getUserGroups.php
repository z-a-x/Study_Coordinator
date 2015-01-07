<?php
$db = new mysqli('localhost', 'root', '', 'study_coordinator');

if($db->connect_errno > 0){
    die('Unable to connect to database [' . $db->connect_error . ']');
}

$grps = $_GET["selected_groups"];

$sql = <<<SQL
    SELECT user.user_id, user.user_name, user.user_last_name, user.username, user_group.group_id 
	FROM `user` 
	INNER JOIN `user_group` 
	ON user.user_id = user_group.user_id
    WHERE user_group.group_id IN $grps
SQL;

$data = array();
if(!$result = $db->query($sql)){
    die('There was an error running the query [' . $db->error . ']');
}
while($row = $result->fetch_assoc()){
    #echo json_encode($row['username']." ".$row['user_name']);
	$data[] = $row;
	
}
$json = json_encode(array('user_group' => $data));
echo $json;
$db->close();

?>
