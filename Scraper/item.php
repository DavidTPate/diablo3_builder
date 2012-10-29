<?php

function getItems($url)
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
	
	$lists = $dom->getElementsByTagName('ul');
	for ($x = 0; $x <= $lists->length -1; $x++)
	{
		$object = $lists->item($x);
		$object_class = trimReplace($object->getAttribute('class'));
		if ($object_class == 'list-items')
		{
			$links = $object->getElementsByTagName('a');
			for ($y = 0; $y <= $links->length - 1; $y++)
			{
				$child = $links->item($y);
				$child_class = trimReplace($child->getAttribute('class'));
				$child_href = trimReplace($child->getAttribute('href'));
				if ($child_class != '')
				{
					if (shouldVisitUrl($base_url.$child_href))
					{
						getItemsFromType($base_url.$child_href);
					}
				}
			}
		}
	}
}

function getItemsFromType($url)
{
	
}
?>