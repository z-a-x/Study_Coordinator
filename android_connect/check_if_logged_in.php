<?php
	if(!isset($_SESSION['id'])) {
		$response["success"] = 0;
        $response["message"] = "You are not logged in!";
        die(json_encode($response));
	} else {
		 $response["success"] = 1;
        $response["message"] = "Already logged in!";
        die(json_encode($response));
		$id = $_SESSION['id'];
		
	}

?>
