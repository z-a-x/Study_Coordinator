<?php
$db = new mysqli('localhost', 'root', '', 'study_coordinator');

if($db->connect_errno > 0){
    die('Unable to connect to database [' . $db->connect_error . ']');
}

if(isset($_GET["action"])) {
    $action = $_GET["action"];
    $id = explode(" ", $action);
    if ($id[0] == "false") {
        $sql = <<<SQL
        INSERT INTO user_event (user_event.user_id, user_event.event_id)
        VALUES ($id[2],$id[1])
SQL;

    }
    else {
        $sql = <<<SQL
        DELETE FROM user_event
        WHERE user_event.user_id = $id[2] and user_event.event_id = $id[1]
SQL;
    }
}
if(!$result = $db->query($sql)){
    die('There was an error running the query [' . $db->error . ']');
}
$response["success"] = 1;
$response["message"] = "Update successful!";
die(json_encode($response));
$db->close();

?>
