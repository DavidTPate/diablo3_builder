<?php

function beginsWith($str, $sub) {
    return (strncmp($str, $sub, strlen($sub)) == 0);
}

function shouldVisitUrl($url)
{
	global $seen;
	
	if (isset($seen[$url]))
	{
		return false;
	}
	else
	{
		$seen[$url] = true;
		return true;
	}
}

function saveFile($filename, $fileUrl)
{
	if (!file_exists($filename))
	{
		file_put_contents($filename, file_get_contents($fileUrl));
		return true;
	}
	return false;
}

function trimReplace($str)
{
	$str = str_replace('\r\n', '', $str);
	$str = str_replace('	', '', $str);
	$str = str_replace('–', '-', $str);
	$str = str_replace('’', '-', $str);
	$str = str_replace('\r\n', '', $str);
	$str = str_replace('\u00e2\u0080\u0099', '\'', $str);
	$str = str_replace('\u00e2\u0080\u0093', '-', $str);
	$str = str_replace('\u00e2\u0080\u00a6', '...', $str);
	$str = str_replace('\u00e2\u0080\u0098', '', $str);
	$str = trim($str);
	
	return $str;
}

function fixFilename($str)
{
	$str = strtolower($str);
	$str = str_replace(' ', '_', $str);
	$str = str_replace('-', '_', $str);

	return $str;
}

function appendConstants()
{
	global $result;
	
	$constants = array(
	'rune-separator' => '!',
	'passive-separator' => '!',
	'missing-value' => '.',
	'follower-separator' => '!'
	);
	
	$skillMapping = array('a','Z','b','Y','c','X','d','W','e','V','f','U','g','T','h','S','i','R','j','Q','k','P','l','O','m','N','n','M','o','L','p','K','q','J','r','I','s','H','t','G','u','F','v','E','w','D','x','C','y','B','z','A');
	
	$followerMapping = array('0','1');
	
	$a = array(
	'key' => 'Left Click',
	'required-level' => 1
	);
	
	$b = array(
	'key' => 'Right Click',
	'required-level' => 2
	);
	
	$c = array(
	'key' => '1',
	'required-level' => 4
	);
	
	$d = array(
	'key' => '2',
	'required-level' => 9
	);
	
	$e = array(
	'key' => '3',
	'required-level' => 14
	);
	
	$f = array(
	'key' => '4',
	'required-level' => 19
	);
	
	$g = array(
	'key' => '',
	'required-level' => 10
	);
	
	$h = array(
	'key' => '',
	'required-level' => 20
	);
	
	$i = array(
	'key' => '',
	'required-level' => 30
	);
	
	$f1 = array(
	'required-level' => 5
	);
	
	$f2 = array(
	'required-level' => 10
	);
	
	$f3 = array(
	'required-level' => 15
	);
	
	$f4 = array(
	'required-level' => 20
	);
	
	$classMapping = array($a, $b, $c, $d, $e, $f, $g, $h, $i);
	$followerMapping = array($f1, $f2, $f3, $f4);
	
	$constants['skill-mapping'] = $skillMapping;
	$constants['skill-levels'] = $classMapping;
	$constants['follower-levels'] = $followerMapping;
	
	$result['skill-attributes'] = $constants;
}

include('main.php');
include('class.php');
include('follower.php');
include('artisan.php');
include('item.php');

getClasses("http://us.battle.net/d3/en/class/");
getFollowers("http://us.battle.net/d3/en/follower/");
//getArtisans("http://us.battle.net/d3/en/artisan/");

//getLinks("http://us.battle.net/d3/en/game/");
appendConstants();

echo trimReplace(json_encode($result));
?>