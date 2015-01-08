<?php
$db = new mysqli('localhost', 'root', '', 'study_coordinator');

if($db->connect_errno > 0){
    die('Unable to connect to database [' . $db->connect_error . ']');
}

$user_id = $_GET["user_id"];

$sql = <<<SQL
    SELECT user_id, user_name, user_last_name, username, email, user_avatar 
	FROM `user` 
	WHERE user_id = $user_id
SQL;

$data = array();
if(!$result = $db->query($sql)){
    die('There was an error running the query [' . $db->error . ']');
}
while($row = $result->fetch_assoc()){
    #echo json_encode($row['username']." ".$row['user_name']);
	$data[] = $row;
	
}
$json = json_encode(array('user' => $data));
echo $json;
$db->close();

?>
