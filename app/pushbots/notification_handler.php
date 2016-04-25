<?php
require_once('PushBots.class.php');
$pb = new PushBots();
$appID = '570aec4c4a9efa28848b4569';
$appSecret = 'ebddcd06ec2f277e49f138485c5a7999';
$pb->App($appID, $appSecret);
 
if ($_SERVER['REQUEST_METHOD'] == 'POST')
{
    $data = json_decode(file_get_contents("php://input"));
    $data = (array) $data;
    
    if($data["token"]){
        if ($data["token"]!="unifacilapptoken") {
         die ("¡Token inválido!");
        } 
        else {
            if($data["email"]) {
                //Simple notification for one user
                $pb->Alias($data["email"]);
            }
            
            $customfields = array();
            
            if ($data["largeIconUrl"]) {
                $customfields["largeIcon"] = $data["largeIconUrl"];
            }
            
            if ($data["nextActivity"]) {
                $customfields["nextActivity"] = $data["nextActivity"];
            }
            
            if($data["bigPictureUrl"]) {
                $customfields["BigPictureStyle"] = true;
                $customfields["imgUrl"] = $data["bigPictureUrl"];
            }
            
            if($data["longMsg"]) {
                $customfields["BigTextStyle"] = "true";
                $customfields["bigText"] = $data["longMsg"];
            } elseif ($data["msg"]) {
                $pb->Alert($data["msg"]);
            }
            
            if($data["params1"] && $data["params2"] && !$data["params3"]) {
                $customfields["params1"] = $data["params1"];
                $customfields["params2"] = $data["params2"];
                $customfields["params3"] = " ";
            }
            
            if($data["params1"] && $data["params2"] && $data["params3"]) {
                $customfields["params1"] = $data["params1"];
                $customfields["params2"] = $data["params2"];
                $customfields["params3"] = $data["params3"];
            }
            
            if($customfields) {
                $pb->Payload($customfields);
            }
            
            $pb->Platform(array("0","1"));
            $pb->Push();
            exit();
        }
              


    }
}




?>



