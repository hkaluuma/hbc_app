

<?php
include "conn.php";
// Check connection

//capture data from front end of the app
//$received_fullname = $_REQUEST['biz_fullnames'];
$received_fullname = $_POST["biz_fullnames"];

$sql = "UPDATE patient SET p_status='sick' WHERE p_fullnames = '$received_fullname' "; 

if ($conn->query($sql) === TRUE) {
    echo "1";
} else {
    echo "0";
}

$conn->close();

?>
