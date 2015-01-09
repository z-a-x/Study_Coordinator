<?php
$db = new mysqli('localhost', 'root', '', 'study_coordinator');

if($db->connect_errno > 0){
    die('Unable to connect to database [' . $db->connect_error . ']');
}

// if id is passed return only event with the id
if(isset($_GET["id"])) {
	$id = $_GET["id"];
	$sql = <<<SQL
	SELECT * FROM `event` WHERE event_id = $id
SQL;
// if search query is passed return events matching query
} else if (isset($_GET["search"])) {
	$query = $_GET["search"];
	$sql = <<<SQL
    SELECT * FROM `event` WHERE event_name LIKE '%$query%' OR description LIKE '%$query%' 
SQL;
// if search query is passed return events matching query
} else if (isset($_GET["group_id"])) {
	$group_id = $_GET["group_id"];
	$sql = <<<SQL
    SELECT * FROM `event` WHERE group_id = $group_id
SQL;
// if nothing is passed return all events 
} else {
	$sql = <<<SQL
    SELECT * FROM `event` 
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
$json = json_encode(array('events' => $data));
echo $json;
$db->close();

?>
