
<?php
require_once "conn.php";

$strKeyword = $_POST["txtKeyword"];
$location = $_POST["location"];
//$strKeyword = "hillary";

//$strSQL = "SELECT * FROM check_up WHERE patient_names LIKE '%".$strKeyword."%' ORDER BY check_up_time DESC LIMIT 10" ;

$strSQL = "SELECT * FROM check_up c INNER JOIN patient p ON c.patient_fk = p.patient_id WHERE c.patient_names LIKE '%".$strKeyword."%' AND p.p_location = '$location' AND p.p_status='sick' ORDER BY p.p_date_created DESC";


$objQuery = mysqli_query($conn, $strSQL);

$intNumField = mysqli_num_fields($objQuery);

$resultArray = array();

while($obResult = mysqli_fetch_array($objQuery))
{

$arrCol = array();
for($i=0;$i<$intNumField;$i++)
{

//mysql_field_name($objQuery,$i)] = $obResult[$i];
$arrCol[mysqli_fetch_field_direct($objQuery, $i)->name] = $obResult[$i];

}

array_push($resultArray,$arrCol);

}

mysqli_close($conn);

echo json_encode($resultArray);

?>