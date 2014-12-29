<?php
$db = new mysqli('localhost', 'root', '', 'study_coordinator');

if($db->connect_errno > 0){
    die('Unable to connect to database [' . $db->connect_error . ']');
}

#$username='z-a-x';	
#$name='Jaka';
#$surname="Plut";
	 
$username=$_REQUEST['username'];
$name=$_REQUEST['userName'];
$surname=$_REQUEST['userLastName'];


$sql = <<<SQL
UPDATE `user` SET `username` = '$username', `user_name` = '$name', `user_last_name` = '$surname'  WHERE `username` LIKE '$username'
SQL;
 
$flag['code']=1;
 
if(!$result = $db->query($sql)){
    die('There was an error running the query [' . $db->error . ']');
	$flag['code']=0;
}
 
print(json_encode($flag));
$db->close();
?>