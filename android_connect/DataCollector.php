<?php
$db = new mysqli('localhost', 'root', '', 'study_coordinator');

if($db->connect_errno > 0){
    die('Unable to connect to database [' . $db->connect_error . ']');
}

#$usr = 'jPlut';
$usr = $_POST["username"];
$sql = <<<SQL
    SELECT user_id, username, user_name, user_last_name, email, user_avatar
    FROM `user`
    WHERE `username` LIKE '$usr'
SQL;

if(!$result = $db->query($sql)){
    die('There was an error running the query [' . $db->error . ']');
}
while($row = $result->fetch_assoc()){
    #echo json_encode($row['username']." ".$row['user_name']);
	echo json_encode($row);
}

$db->close();

?>
