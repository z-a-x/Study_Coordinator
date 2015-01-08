<?php
$db = new mysqli('localhost', 'root', '', 'study_coordinator');

if($db->connect_errno > 0){
    die('Unable to connect to database [' . $db->connect_error . ']');
}

#$username='z-a-x';	
#$name='Jaka';
#$surname="Plut";
	 

	 
$username=$_REQUEST['username'];
$email=$_REQUEST['email'];
$name=$_REQUEST['userName'];
$surname=$_REQUEST['userLastName'];

if(isset($_REQUEST["user_avatar"])) {
	$user_avatar = $_REQUEST["user_avatar"];
	$sql = <<<SQL
	UPDATE `user` SET `email` = '$email', `user_name` = '$name', `user_last_name` = '$surname', `user_avatar` = '$user_avatar'  WHERE `username` LIKE '$username'
SQL;
}
else {
	$sql = <<<SQL
	UPDATE `user` SET `email` = '$email', `user_name` = '$name', `user_last_name` = '$surname'  WHERE `username` LIKE '$username'
SQL;
}
 
$flag['code']=1;
 
if(!$result = $db->query($sql)){
    die('There was an error running the query [' . $db->error . ']');
	$flag['code']=0;
}
 
print(json_encode($flag));
$db->close();
?>