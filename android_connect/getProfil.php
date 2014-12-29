<?php
require("db_config.php");

$username = $_POST['username'];
$user_name = $_POST['password'];
$user_last_name = $_POST['user_last_name'];
$user_avatar = $_POST['user_avatar'];
$result = mysqli_query($con,"SELECT username FROM user where username = 123");
$row = mysqli_fetch_array($result);
$data = $row[0];

if($data){
print(json_encode($data));
}


?>

