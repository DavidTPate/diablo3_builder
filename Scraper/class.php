<?php

function getClasses($url)
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
		
		$classes = array();
		
		$count = 0;
		$classNames = $dom->getElementsByTagName('h3');
		for ($x = 0; $x <= $classNames->length -1; $x++)
		{
			$object = $classNames->item($x);
			$object_class = trimReplace($object->getAttribute('class'));
			if ($object_class == 'category')
			{
				$classes[$count]['name'] = trimReplace($object->nodeValue);
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
					if (isset($classes[$count]['short-description']))
					{
						$classes[$count]['short-description'] .= trimReplace($child->nodeValue);
					}
					else
					{
						$classes[$count]['short-description'] = trimReplace($child->nodeValue);
					}
					
				}
				$count++;
			}
		}
		
		$result['classes'] = $classes;
		
		//$i = 0;
		
		$count = 0;
		$links = $dom->getElementsByTagName('a');
		for ($x = 0; $x <= $links->length - 1; $x++)
		{
			$object = $links->item($x);
			$object_href = trimReplace($object->getAttribute('href'));
			$object_class = trimReplace($object->getAttribute('class'));
			if (preg_match('~^/d3/'.$locale.'/class/.+~',$object_href) && $object_class == '')
			{
				$classUrl = $base_url.$object_href;
				if (shouldVisitUrl($classUrl))
				{
					//$i++;
					//if ($i == 1)
					//{
						getClassDetails($classUrl, $count);
						$count++;
					//}
				}
			}
		}
	}
}

function getClassDetails($url, $index)
{
	global $result;		
	global $base_url;
	global $region;
	global $locale;
	
	$dom = new DOMDocument('1.0');
	@$dom->loadHTMLFile($url);
	
	$class = $result['classes'][$index];
	
	$features = array();
	
	$divs = $dom->getElementsByTagName('div');
	for ($x = 0; $x <= $divs->length -1; $x++)
	{
		$object = $divs->item($x);
		$object_class = trimReplace($object->getAttribute('class'));
		$object_value = trimReplace($object->nodeValue);
		if ($object_class == 'gameplay-text')
		{
			for ($y = 0; $y <= $object->childNodes->length - 1; $y++)
			{
				$child = $object->childNodes->item($y);
				if (!isset($class['description']))
				{
					$class['description'] = '';
				}
				$class['description'] .= ' '.trimReplace($child->nodeValue);
			}
		}
		else if ($object_class == 'db-page-jumper')
		{
			$spans = $object->getElementsByTagName('span');
			for ($y = 0; $y <= $spans->length - 1; $y++)
			{
				$child = $spans->item($x);
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
					
					if ($val == str_replace(' ', '',strtolower($class['name'].'_female')))
					{
						$image_url2 = str_replace('_female', '_male', $image_url);
						$filename2 = str_replace('_female', '_male', $val);
						$class['icon_female'] = $val;
						$class['icon_male'] = str_replace('_female', '_male', $val);
					}
					else if ($val == str_replace(' ', '',strtolower($class['name'].'_male')))
					{
						$image_url2 = str_replace('_male', '_female', $image_url);
						$filename2 = str_replace('_female', '_male', $val);
						$class['icon_male'] = $val;
						$class['icon_female'] = str_replace('_male', '_female', $val);
					}
					
					$directory = 'D:\\Dropbox\\Projects\\Diablo 3 Site Crawler\\Images\\';
					
					saveFile($directory.fixFilename($filename),$image_url);
					//saveFile($directory.fixFilename($filename),str_replace('64', '32', $image_url));
					
					saveFile($directory.fixFilename($filename2),$image_url2);
					//saveFile($directory.fixFilename($filename2),str_replace('64', '32', $image_url2));
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
						$filename = strtolower($class['name']).'-'.$matches[1][0].$matches[2][0];
						$val = strtolower($class['name']).'-'.$matches[1][0];
						$features[$count]['icon'] = $val;
						
						$directory = 'D:\\Dropbox\\Projects\\Diablo 3 Site Crawler\\Images\\';
						
						saveFile($directory.fixFilename($filename),$base_url.$image_url);
					}
				}
				$count++;
			}
			$class['features'] = $features;
		}
		else if ($object_class == 'lore-text')
		{
			$paras = $object->getElementsByTagName('p');
			for ($y = 0; $y <= $paras->length - 1; $y++)
			{
				$child = $paras->item($y);
				$child_class = trimReplace($child->getAttribute('class'));
				$child_value = trimReplace($child->nodeValue);
				
				if ($child_class == '')
				{
					if (!isset($class['lore']))
					{
						$class['lore'] = '';
					}
					$class['lore'] .= ' '.$child_value;
				}
			}
		}
		else if ($object_class == 'page-section equipment')
		{
			$paras = $object->getElementsByTagName('p');
			for ($y = 0; $y <= $paras->length - 1; $y++)
			{
				$child = $paras->item($y);
				$child_class = trimReplace($child->getAttribute('class'));
				$child_value = trimReplace($child->nodeValue);
				if ($child_class == '')
				{
					if (!isset($class['equipment']))
					{
						$class['equipment'] = '';
					}
					$class['equipment'] .= ' '.$child_value;
				}
			}
		}
		else if ($object_class == 'resource-top')
		{
			$paras = $object->getElementsByTagName('p');
			for ($y = 0; $y <= $paras->length - 1; $y++)
			{
				$child = $paras->item($y);
				$child_class = trimReplace($child->getAttribute('class'));
				$child_value = trimReplace($child->nodeValue);
				if ($child_class == '')
				{
					if (!isset($class['resource']))
					{
						$class['resource'] = '';
					}
					$class['resource'] .= ' '.$child_value;
				}
			}
			
			$header = $object->getElementsByTagName('h3');
			for ($y = 0; $y <= $header->length - 1; $y++)
			{
				$child = $header->item($y);
				$child_class = trimReplace($child->getAttribute('class'));
				$child_value = trimReplace($child->nodeValue);
				if ($child_class == 'category')
				{
					$class['resource-type'] = trimReplace(str_replace('Resource:', '', $child_value));
				}
			}
		}
		else if ($object_class == 'tier tier-1')
		{
			$class['tier-1'] = $object_value;
		}
		else if ($object_class == 'tier tier-2')
		{
			$class['tier-2'] = $object_value;
		}
		else if ($object_class == 'tier tier-3')
		{
			$class['tier-3'] = $object_value;
		}
	}
	
	$result['classes'][$index] = $class;
	
	$links = $dom->getElementsByTagName('a');
	for ($x = 0; $x <= $links->length - 1; $x++)
	{
		$object = $links->item($x);
		$object_href = trimReplace($object->getAttribute('href'));
		$object_class = trimReplace($object->getAttribute('class'));
		if (preg_match('~^/d3/'.$locale.'/class/.*/active/$~',$object_href) && $object_class == '')
		{
			$skillsUrl = $base_url.$object_href;
			if (shouldVisitUrl($skillsUrl))
			{
				getClassSkills($skillsUrl, $index, 'active');
			}
		}
		else if (preg_match('~^/d3/'.$locale.'/class/.*/passive/$~',$object_href) && $object_class == '')
		{
			$skillsUrl = $base_url.$object_href;
			if (shouldVisitUrl($skillsUrl))
			{
				getClassSkills($skillsUrl, $index, 'passive');
			}
		}
		else if (preg_match('~^/d3/'.$locale.'/media/screenshots/.*$~',$object_href) && $object_class == '')
		{
			$screenshotsUrl = $base_url.$object_href.'#/1';
			if (shouldVisitUrl($screenshotsUrl))
			{
				getClassScreenshots($screenshotsUrl, $index);
			}
		}
	}
}

function getClassSkills($url, $index, $type)
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
		if (preg_match('~^/d3/'.$locale.'/class/.*/'.$type.'/[^\?].+$~',$object_href) && $object_class == '')
		{
			$skillUrl = $base_url.$object_href;
			if (shouldVisitUrl($skillUrl))
			{
				getClassSkill($skillUrl, $index, $type);
			}
		}
	}
}

function getClassSkill($url, $index, $type)
{
	global $result;		
	global $base_url;
	global $region;
	global $locale;
	
	$dom = new DOMDocument('1.0');
	@$dom->loadHTMLFile($url);
	
	$skill = array();
	
	if ($type == 'passive')
	{
		$skill['type'] = 'Passive';
	}
	
	$classNames = $dom->getElementsByTagName('meta');
	for ($x = 0; $x <= $classNames->length -1; $x++)
	{
		$object = $classNames->item($x);
		$object_name = trimReplace($object->getAttribute('name'));
		if ($object_name == 'title')
		{
			$skill['name'] =  trimReplace($object->getAttribute('content'));
		}
	}
	
	$classNames = $dom->getElementsByTagName('span');
	for ($x = 0; $x <= $classNames->length -1; $x++)
	{
		$object = $classNames->item($x);
		$object_class = trimReplace($object->getAttribute('class'));
		if ($object_class == 'detail-level-unlock')
		{
			$skill['required-level'] =  intval(trimReplace($object->nodeValue));
		}
	}
	
	$classNames = $dom->getElementsByTagName('div');
	for ($x = 0; $x <= $classNames->length -1; $x++)
	{
		$object = $classNames->item($x);
		$object_class = trimReplace($object->getAttribute('class'));
		if ($object_class == 'skill-desc')
		{		
			$paras = $object->getElementsByTagName('p');
			for ($y = 0; $y <= $paras->length - 1; $y++)
			{
				$child = $paras->item($y);
				$child_value = trimReplace($child->nodeValue);
				if (preg_match_all('~(Generate|Cost|Cooldown):.?([0-9]*).?(Fury|Hatred|Discipline|Spirit|Mana|seconds) ?(per attack)?~', $child_value, $matches))
				{
					if (strtolower(trimReplace($matches[1][0])) == 'generate')
					{
						$skill['generate'] = intval(trimReplace($matches[2][0]));
						$skill['generate-units'] = trimReplace($matches[3][0]);
						if (isset($matches[4][0]))
						{
							$skill['generate-description'] = trimReplace($matches[4][0]);
						}
					}
					else if (strtolower(trimReplace($matches[1][0])) == 'cost')
					{
						$skill['cost'] = intval(trimReplace($matches[2][0]));
						$skill['cost-units'] = trimReplace($matches[3][0]);
						if (isset($matches[4][0]))
						{
							$skill['cost-description'] = trimReplace($matches[4][0]);
						}
					}
					else if (strtolower(trimReplace($matches[1][0])) == 'cooldown')
					{
						$skill['cooldown'] = intval(trimReplace($matches[2][0]));
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
					
					if (isset($matches[1][1]))
					{					
						if (strtolower(trimReplace($matches[1][1])) == 'generate')
						{
							$skill['generate'] = intval(trimReplace($matches[2][1]));
							$skill['generate-units'] = trimReplace($matches[3][1]);
							if (isset($matches[4][1]))
							{
								$skill['generate-description'] = trimReplace($matches[4][1]);
							}
						}
						else if (strtolower(trimReplace($matches[1][1])) == 'cost')
						{
							$skill['generate'] = intval(trimReplace($matches[2][1]));
							$skill['generate-units'] = trimReplace($matches[3][1]);
							if (isset($matches[4][1]))
							{
								$skill['generate-description'] = trimReplace($matches[4][1]);
							}
						}
						else if (strtolower(trimReplace($matches[1][1])) == 'cooldown')
						{
							$skill['cooldown'] = intval(trimReplace($matches[2][1]));
							$skill['cooldown-units'] = trimReplace($matches[3][1]);
							if (isset($matches[4][1]))
							{
								$skill['cooldown-description'] = trimReplace($matches[4][1]);
							}
						}
						else
						{
							if (!isset($skill['description']))
							{
								$skill['description'] = '';
							}
							$skill['description'] .= ' '.trimReplace($child_value);
						}
					}
				}
				else if (preg_match('~.*?This is a Signature spell\. Signature spells are free to cast\..*?~', $child_value))
				{
					$skill['signature-spell'] = true;
				}
				else
				{
					if (!isset($skill['description']))
					{
						$skill['description'] = '';
					}
					$skill['description'] .= ' '.trimReplace($child_value);
				}
			}
		}
		if ($object_class == 'skill-category')
		{
			$skill['type'] = trimReplace($object->nodeValue);
		}
		if ($object_class == 'table rune-list')
		{
			$runes = array();
			$count = 0;
			
			$rows = $object->getElementsByTagName('tr');
			for ($y = 0; $y <= $rows->length - 1; $y++)
			{
				$row = $rows->item($y);
				$row_class = trimReplace($row->getAttribute('class'));
				if ($row_class != '')
				{
					$columns = $row->getElementsByTagName('td');
					for ($z = 0; $z <= $columns->length - 1; $z++)
					{
						$child = $columns->item($z);
						$child_class = $child->getAttribute('class');
						if ($child_class == 'column-level align-center')
						{
							$runes[$count]['required-level'] = intval(trimReplace($child->nodeValue));
						}
						else if ($child_class == 'column-rune')
						{
							$h4s = $child->getElementsByTagName('h4');
							for ($n = 0; $n <= $h4s->length - 1; $n++)
							{
								$h4 = $h4s->item($n);
								$h4_class = trimReplace($h4->getAttribute('class'));
								if ($h4_class == 'subcategory')
								{
									$runes[$count]['name'] = trimReplace($h4->nodeValue);
								}
							}
							
							$divs = $child->getElementsByTagName('div');
							for ($n = 0; $n <= $divs->length - 1; $n++)
							{
								$div = $divs->item($n);
								$div_class = trimReplace($div->getAttribute('class'));
								if ($div_class == 'rune-desc')
								{
									$runes[$count]['description'] = trimReplace($div->nodeValue);
								}
							}
							
							$spans = $child->getElementsByTagName('span');
							for ($n = 0; $n <= $spans->length - 1; $n++)
							{
								$span = $spans->item($n);
								$span_class = trimReplace($span->getAttribute('class'));
								if (beginsWith(trimReplace($span_class), 'rune-'))
								{
									$runes[$count]['icon'] = fixFilename($span_class);
								}
							}
						}
					}
					$count++;
				}
			}
			
			$skill['runes'] = $runes;
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
					$skill['icon'] = $val;
					$directory = 'D:\\Dropbox\\Projects\\Diablo 3 Site Crawler\\Images\\';
					
					saveFile($directory.fixFilename($filename),$image_url);
				}
			}
		}
	}
	
	$class = $result['classes'][$index];
	$skills = array();
	if (isset($class[$type.'-skills']))
	{
		$skills = $class[$type.'-skills'];
	}
	
	if (isset($skill['type']))
	{
		$attrbs = array();
		if (isset($result['class-attributes']))
		{
			$attrbs = $result['class-attributes'];
		}
		
		$classAttrbs = array();
		if (isset($attrbs[$index]))
		{
			$classAttrbs = $attrbs[$index];
		}
		else
		{
			$classAttrbs['name'] = $class['name'];
		}
		
		$planTypes = array();
		if (isset($classAttrbs['skill-types']))
		{
			$planTypes = $classAttrbs['skill-types'];
		}
		
		if (!in_array($skill['type'], $planTypes) && $skill['type'] != 'Passive')
		{
			array_push($planTypes, $skill['type']);
			
			$classAttrbs['skill-types'] = $planTypes;
			$attrbs[$index] = $classAttrbs;
			$result['class-attributes'] = $attrbs;
		}
		
	}
	
	array_push($skills, $skill);
	
	$class[$type.'-skills'] = $skills;
	
	$result['classes'][$index] = $class;
}

function getClassScreenshots($url, $index)
{
	global $result;		
	global $base_url;
	global $region;
	global $locale;
	
	preg_match_all('~^.*#/([0-9].?)~', $url, $matches);
	$curr_page = $matches[1][0];
	
	$dom = new DOMDocument('1.0');
	@$dom->loadHTMLFile($url);
	
	$class = $result['classes'][$index];
	
	if (!isset($class['screenshots-count']))
	{
		$class['screenshots-count'] = 0;
	}
	
	$count = $class['screenshots-count'];
	
	$screens = $dom->getElementsByTagName('span');
	for ($x = 0; $x <= $screens->length - 1; $x++)
	{
		$object = $screens->item($x);
		$object_url = trimReplace($object->getAttribute('data-thumbsrc'));
		$object_class = trimReplace($object->getAttribute('class'));
		
		if ($object_class == 'thumb-frame')
		{
			if (preg_match_all('~^.*/(.*)(\..*)$~', $object_url, $matches))
			{
				$filename = $matches[1][0].$matches[2][0];
				$class['screenshots'][$count] = $matches[1][0];

				$directory = 'D:\\Dropbox\\Projects\\Diablo 3 Site Crawler\\Images\\';
				saveFile($directory.fixFilename($filename),$object_url);
				$count++;
			}
		}
	}
	
	$class['screenshots-count'] = $count;
	$result['classes'][$index] = $class;
	
	//Site is organized weirdly, so can't see anymore than the first page.
	//Javascript dependent sites...sigh
	
	return;
	
	$pages = $dom->getElementsByTagName('ul');
	for ($x = 0; $x <= $pages->length - 1; $x++)
	{
		$object = $pages->item($x);
		$object_class = trimReplace($object->getAttribute('class'));
		
		if ($object_class == 'ui-pagination')
		{
			$spans = $object->getElementsByTagName('span');
			for ($y = 0; $y <= $spans->length - 1; $y++)
			{
				$child = $spans->item($y);
				$child_class = trimReplace($child->getAttribute('class'));
				$child_value = trimReplace($child->nodeValue);
				if ($child_class == '')
				{
					if ($curr_page < $child_value)
					{
						$screenshotPageUrl = str_replace('#/'.$curr_page, '#/'.$child_value, $url);
						if (shouldVisitUrl($screenshotPageUrl))
						{
							getClassScreenshots($screenshotPageUrl, $index);
						}
					}
				}
			}
		}		
	}
}
?>