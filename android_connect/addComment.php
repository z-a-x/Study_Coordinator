<?php
$db = new mysqli('localhost', 'root', '', 'study_coordinator');

if($db->connect_errno > 0){
    die('Unable to connect to database [' . $db->connect_error . ']');
}

if(isset($_GET["action"])) {
    $action = $_GET["action"];
    $id = explode(".", $action);
        $sql = <<<SQL
        INSERT INTO comment (comment.event_id, comment.user_id, comment.date_time, comment.text)
        VALUES ($id[0],$id[1], $id[2], $id[3])
SQL;

}
if(!$result = $db->query($sql)){
    die('There was an error running the query [' . $db->error . ']');
}
$response["success"] = 1;
$response["message"] = "Update successful!";
die(json_encode($response));
$db->close();

?>
