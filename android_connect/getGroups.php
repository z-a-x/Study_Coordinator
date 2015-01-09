<?php
$db = new mysqli('localhost', 'root', '', 'study_coordinator');

if($db->connect_errno > 0){
    die('Unable to connect to database [' . $db->connect_error . ']');
}


// if id is passed return only group with the id
if(isset($_GET["id"])) {
	$id = $_GET["id"];
	$sql = <<<SQL
   	SELECT group_id, group_name, groups_owner FROM `groups` WHERE group_id=$id
SQL;
// if search query is passed return groups matching query
} else if (isset($_GET["search"])) {
	$query = $_GET["search"];
	$sql = <<<SQL
   	SELECT group_id, group_name, groups_owner FROM `groups` WHERE group_name LIKE '%$query%' 
SQL;
// if nothing is passed return all groups
} else if (isset($_GET["user_id"])) {
	
	$user_id = $_GET["user_id"];
	$sql = <<<SQL
   	SELECT groups.group_id, groups.group_name, groups.groups_owner 
	FROM `groups` 
	INNER JOIN `user_group` 
	ON groups.group_id = user_group.group_id 
	AND user_group.user_id = $user_id
SQL;
// if nothing is passed return all groups
} 

else {
	$sql = <<<SQL
   	SELECT group_id, group_name, groups_owner FROM `groups` 
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
$json = json_encode(array('groups' => $data));
echo $json;
$db->close();

?>
