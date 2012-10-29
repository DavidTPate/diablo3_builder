<?php

function getFollowers($url)
{
	//Make sure the URL hasn't already been visited.
	if (shouldVisitUrl($url))
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
		
		$followers = array();
		
		$count = 0;
		$followerNames = $dom->getElementsByTagName('h3');
		for ($x = 0; $x <= $followerNames->length -1; $x++)
		{
			$object = $followerNames->item($x);
			$object_class = trimReplace($object->getAttribute('class'));
			if ($object_class == 'category')
			{
				$followers[$count]['name'] = trimReplace($object->nodeValue);
				$followers[$count]['icon'] = trimReplace(strtolower($object->nodeValue));
				$count++;
			}
		}
		
		$count = 0;
		$blurbs = $dom->getElementsByTagName('span');
		for ($x = 0; $x <= $blurbs->length -1; $x++)
		{
			$object = $blurbs->item($x);
			$object_class = trimReplace($object->getAttribute('class'));
			if ($object_class == 'blurb')
			{
				for ($y = 0; $y <= $object->childNodes->length - 1; $y++)
				{
					$child = $object->childNodes->item($y);
					if (isset($followers[$count]['short-description']))
					{
						$followers[$count]['short-description'] .= trimReplace($child->nodeValue);
					}
					else
					{
						$followers[$count]['short-description'] = trimReplace($child->nodeValue);
					}
					
				}
				$count++;
			}
		}
		
		$result['followers'] = $followers;
		
		$count = 0;
		$links = $dom->getElementsByTagName('a');
		for ($x = 0; $x <= $links->length - 1; $x++)
		{
			$object = $links->item($x);
			$object_href = trimReplace($object->getAttribute('href'));
			$object_class = trimReplace($object->getAttribute('class'));
			if (preg_match('~^/d3/'.$locale.'/follower/.+~',$object_href) && $object_class == '')
			{
				$followerUrl = $base_url.$object_href;
				if (shouldVisitUrl($followerUrl))
				{
					getFollowerDetails($followerUrl, $count);
					$count++;
				}
			}
		}
	}
}

function getFollowerDetails($url, $index)
{
	global $result;		
	global $base_url;
	global $region;
	global $locale;
	
	$dom = new DOMDocument('1.0');
	@$dom->loadHTMLFile($url);
	
	$follower = $result['followers'][$index];
	
	$features = array();
	
	$divs = $dom->getElementsByTagName('div');
	for ($x = 0; $x <= $divs->length -1; $x++)
	{
		$object = $divs->item($x);
		$object_class = trimReplace($object->getAttribute('class'));
		if ($object_class == 'intro')
		{
			for ($y = 0; $y <= $object->childNodes->length - 1; $y++)
			{
				$child = $object->childNodes->item($y);
				if (!isset($follower['description']))
				{
					$follower['description'] = '';
				}
				$follower['description'] .= ' '.trimReplace($child->nodeValue);
			}
		}
		else if ($object_class == 'db-page-jumper')
		{
			$spans = $object->getElementsByTagName('span');
			for ($y = 0; $y <= $spans->length - 1; $y++)
			{
				$child = $spans->item($y);
				$child_class = trimReplace($child->getAttribute('class'));
				if ($child_class == 'icon-portrait icon-frame')
				{
					$child_style = trimReplace($child->getAttribute('style'));
					preg_match_all('~.*url\(\'(.*)\'\)~', $child_style, $matches);
					$image_url = $matches[1][0];
					
					preg_match_all('~^.*/(.*)(\..*)$~', $image_url, $matches);
					$filename = $matches[1][0].$matches[2][0];
					$image_url2 = '';
					$filename2 = '';
					$val = $matches[1][0];
					
					$directory = 'D:\\Dropbox\\Projects\\Diablo 3 Site Crawler\\Images\\';
					
					if (saveFile($directory.fixFilename($filename),$image_url))
					{
						$follower['icon'] = $val;
					}
				}
			}
		}
		else if ($object_class == 'features-top')
		{
			$count = 0;
			$lis = $object->getElementsByTagName('li');
			for ($y = 0; $y <= $lis->length - 1; $y++)
			{
				$li = $lis->item($y);
				$children = $li->getElementsByTagName('h4');
				for ($z = 0; $z <= $children->length - 1; $z++)
				{
					$child = $children->item($z);
					$child_class = trimReplace($child->getAttribute('class'));
					$child_value = trimReplace($child->nodeValue);
					if ($child_class == 'subcategory')
					{
						$features[$count]['name'] = $child_value;
					}
				}
				
				$children = $li->getElementsByTagName('span');
				for ($z = 0; $z <= $children->length - 1; $z++)
				{
					$child = $children->item($z);
					$child_class = trimReplace($child->getAttribute('class'));
					$child_value = trimReplace($child->nodeValue);
					if ($child_class == 'desc')
					{
						$features[$count]['description'] = $child_value;
					}
					else if ($child_class == 'd3-icon d3-icon-trait d3-icon-trait-64 circle')
					{
						$child_style = trimReplace($child->getAttribute('style'));
						preg_match_all('~.*url\(\'(.*)\'\)~', $child_style, $matches);
						$image_url = $matches[1][0];
						
						preg_match_all('~^.*/(.*)(\..*)$~', $image_url, $matches);
						$filename = strtolower($follower['name']).'-'.$matches[1][0].$matches[2][0];
						$val = strtolower($follower['name']).'-'.$matches[1][0];
						$features[$count]['icon'] = $val;
						
						$directory = 'D:\\Dropbox\\Projects\\Diablo 3 Site Crawler\\Images\\';
						
						saveFile($directory.fixFilename($filename),$base_url.$image_url);
					}
				}
				$count++;
			}
			$follower['features'] = $features;
		}
	}
	
	$result['followers'][$index] = $follower;
	
	$links = $dom->getElementsByTagName('a');
	for ($x = 0; $x <= $links->length - 1; $x++)
	{
		$object = $links->item($x);
		$object_href = trimReplace($object->getAttribute('href'));
		$object_class = trimReplace($object->getAttribute('class'));
		if (preg_match('~^/d3/'.$locale.'/follower/.*/skill/$~',$object_href) && $object_class == '')
		{
			$skillsUrl = $base_url.$object_href;
			if (shouldVisitUrl($skillsUrl))
			{
				getFollowerSkills($skillsUrl, $index);
			}
		}
	}
}

function getFollowerSkills($url, $index)
{	
	global $base_url;
	global $locale;
	
	$dom = new DOMDocument('1.0');
	@$dom->loadHTMLFile($url);
	
	$links = $dom->getElementsByTagName('a');
	for ($x = 0; $x <= $links->length - 1; $x++)
	{
		$object = $links->item($x);
		$object_href = trimReplace($object->getAttribute('href'));
		$object_class = trimReplace($object->getAttribute('class'));
		if (preg_match('~^/d3/'.$locale.'/follower/.*/.*/[^\?].+$~',$object_href) && $object_class == '')
		{
			$skillUrl = $base_url.$object_href;
			if (shouldVisitUrl($skillUrl))
			{
				getFollowerSkill($skillUrl, $index);
			}
		}
	}
}

function getFollowerSkill($url, $index)
{
	global $result;		
	global $base_url;
	global $region;
	global $locale;
	
	$dom = new DOMDocument('1.0');
	@$dom->loadHTMLFile($url);
	
	$skill = array();
	
	$meta = $dom->getElementsByTagName('meta');
	for ($x = 0; $x <= $meta->length - 1; $x++)
	{
		$object = $meta->item($x);
		$object_name = trimReplace($object->getAttribute('name'));
		if ($object_name == 'title')
		{
			$skill['name'] =  trimReplace($object->getAttribute('content'));
		}
	}
	
	$span = $dom->getElementsByTagName('span');
	for ($x = 0; $x <= $span->length -1; $x++)
	{
		$object = $span->item($x);
		$object_class = trimReplace($object->getAttribute('class'));
		if ($object_class == 'detail-level-unlock')
		{
			$skill['required-level'] =  trimReplace($object->nodeValue);
		}
	}
	
	$div = $dom->getElementsByTagName('div');
	for ($x = 0; $x <= $div->length -1; $x++)
	{
		$object = $div->item($x);
		$object_class = trimReplace($object->getAttribute('class'));
		if ($object_class == 'skill-desc')
		{			
			for ($y = 0; $y <= $object->childNodes->length - 1; $y++)
			{
				$child = $object->childNodes->item($y);
				$child_value = trimReplace($child->nodeValue);
				if (preg_match_all('~(Generate|Cost|Cooldown):.?([0-9]*).?(Fury|Hatred|Discipline|Spirit|Mana|seconds).?(per attack|.*)?~', $child_value, $matches))
				{
					if (strtolower(trimReplace($matches[1][0])) == 'generate')
					{
						$skill['cooldown'] = trimReplace($matches[2][0]);
						$skill['cooldown-units'] = trimReplace($matches[3][0]);
						if (isset($matches[4][0]))
						{
							$skill['cost-description'] = trimReplace($matches[4][0]);
						}
					}
					else if (strtolower(trimReplace($matches[1][0])) == 'cost')
					{
						$skill['generate'] = trimReplace($matches[2][0]);
						$skill['generate-units'] = trimReplace($matches[3][0]);
						if (isset($matches[4][0]))
						{
							$skill['generate-description'] = trimReplace($matches[4][0]);
						}
					}
					else if (strtolower(trimReplace($matches[1][0])) == 'cooldown')
					{
						$skill['cooldown'] = trimReplace($matches[2][0]);
						$skill['cooldown-units'] = trimReplace($matches[3][0]);
						if (isset($matches[4][0]))
						{
							$skill['cooldown-description'] = trimReplace($matches[4][0]);
						}
					}
					else
					{
						if (!isset($skill['description']))
						{
							$skill['description'] = '';
						}
						$skill['description'] .= ' '.$child_value;
					}
				}
				else if (preg_match('~.*?Signature spell.*?~', $child_value))
				{
					$skill['signature-spell'] = true;
				}
				else
				{
					if (!isset($skill['description']))
					{
						$skill['description'] = '';
					}
					$skill['description'] .= ' '.$child_value;
				}
			}
		}
		else if ($object_class == 'db-detail-box colors-subtle skill-detail' || $object_class == 'db-detail-box colors-subtle skill-detail has-skill-video')
		{
			$spans = $object->getElementsByTagName('span');
			for ($y = 0; $y <= $spans->length - 1; $y++)
			{
				$span = $spans->item($y);
				$span_class = trimReplace($span->getAttribute('class'));
				if ($span_class == 'd3-icon d3-icon-skill d3-icon-skill-64' || $span_class == 'd3-icon d3-icon-trait  d3-icon-trait-64')
				{
					preg_match_all('~.*url\(\'(.*)\'\)~', $span->getAttribute('style'), $matches);
					$image_url = $matches[1][0];
					preg_match_all('~^.*/(.*)(\..*)$~', $image_url, $matches);
					$filename = $matches[1][0].$matches[2][0];
					$val = $matches[1][0];
					$icon = $val;
					$values = explode($val, '.');
					$directory = 'D:\\Dropbox\\Projects\\Diablo 3 Site Crawler\\Images\\';

					if (!file_exists($directory.fixFilename($filename)))
					{
						file_put_contents($directory.fixFilename($filename), file_get_contents($image_url));
					}
				}
			}
		}
	}
	
	$follower = $result['followers'][$index];
	$skills = array();
	if (isset($follower['skills']))
	{
		$skills = $follower['skills'];
	}
	
	array_push($skills, $skill);
	
	$follower['skills'] = $skills;
	
	$result['followers'][$index] = $follower;
}
?>