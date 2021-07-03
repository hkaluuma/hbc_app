<?php
include "conn.php";

$received_username = $_POST['biz_username'];
$received_password = $_POST['biz_pass'];
//$received_password = md5($password);
//$received_username = "hilla";
//$received_password = "1234";

//check connection
if($conn->connect_error){
	die("connection failed: " . $conn->connect_error);
}
$sql =  "SELECT * FROM vht_officer WHERE vht_username = '$received_username' AND vht_password = '$received_password'";
$result = $conn->query($sql);
if($result->num_rows >0){
	//output data for each row
	while($row = $result->fetch_assoc()){
		echo $row["vht_username"]."#";
		echo $row["vht_fullname"]."#";
		echo $row["vht_phone_number"]."#";
		echo $row["vht_location"]."#";
		echo $row["vht_email"]."#";
		echo $row["vht_password"]."#";
		echo $row["vht_id"]."#";
		
	}
}else {
		echo "0";
	}

//close the connection to db
$conn->close();

?>