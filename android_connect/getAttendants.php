<?php
$db = new mysqli('localhost', 'root', '', 'study_coordinator');

if($db->connect_errno > 0){
    die('Unable to connect to database [' . $db->connect_error . ']');
}

// if id is passed return only event with the id
if(isset($_GET["id"])) {
    $id = $_GET["id"];
    $sql = <<<SQL
    SELECT user.user_id, user.user_name, user.user_last_name
	FROM user_event, user
	WHERE user.user_id =  user_event.user_id and user_event.event_id = $id
	ORDER BY user.user_name
SQL;
} else {
    $sql = <<<SQL
    SELECT user.user_id, user.user_name, user.user_last_name
	FROM user
	ORDER BY user.user_name

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
$json = json_encode(array('user_event' => $data));
echo $json;
$db->close();

?>
