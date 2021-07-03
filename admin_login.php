<?php
include "conn.php";
// Check connection

//capture data from front end of the app
$received_username = $_POST['biz_username'];
$received_password = $_POST['biz_pass'];

//$received_username = "hilla";
//$received_password = "123";
//$received_password = md5($password);

$sql = "SELECT * FROM admin WHERE admin_username = '$received_username' AND admin_password = '$received_password'";

$objQuery = mysqli_query($conn, $sql);
$objResult = mysqli_fetch_array($objQuery);
$intNumRows = mysqli_num_rows($objQuery);
if($intNumRows==0)
{
echo "0";
//echo "Incorrect Username and Password"; 
}
else
{
echo "1";
//echo "correct password and username";
}

$conn->close();

?>
