<?php

function getLinks($url)
{
	global $result;
	
	//Find the base URL
	preg_match_all('~^(http://.{2}\.battle\.net)/d3/.{2}/.+$~', $url, $matches);
	
	global $base_url;
	$base_url = $matches[1][0];
	
	//Find the region and locale
	preg_match_all('~^http://([a-z]{2})\.battle\.net/d3/([a-z]{2})/.+$~', $url, $matches);
	global $region;
	$region = $matches[1][0];
	global $locale;
	$locale = $matches[2][0];
	
	$dom = new DOMDocument('1.0');
	@$dom->loadHTMLFile($url);
	
	$links = $dom->getElementsByTagName('a');
	for ($x = 0; $x <= $links->length -1; $x++)
	{
		$object = $links->item($x);
		$object_class = trimReplace($object->getAttribute('class'));
		$object_href = trimReplace($object->getAttribute('href'));
		$object_value = trimReplace($object->nodeValue);
		if ($object_value == 'Classes')
		{
			getClasses($base_url.$object_href);
		}
		else if ($object_value == 'Artisans')
		{
			//getArtisans($base_url.$object_href);
		}
		else if ($object_value == 'Items')
		{
			//getItems($base_url.$object_href);
		}
		else if ($object_value == 'Followers')
		{
			getFollowers($base_url.$object_href);
		}
	}
}
?>