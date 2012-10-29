<?php

function getArtisans($url)
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
		
		$artisans = array();
		
		$count = 0;
		$artisanNames = $dom->getElementsByTagName('h3');
		for ($x = 0; $x <= $artisanNames->length -1; $x++)
		{
			$object = $artisanNames->item($x);
			$object_class = trimReplace($object->getAttribute('class'));
			if ($object_class == 'category')
			{
				$artisans[$count]['name'] = trimReplace($object->nodeValue);
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
					if (isset($artisans[$count]['short-description']))
					{
						$artisans[$count]['short-description'] .= trimReplace($child->nodeValue);
					}
					else
					{
						$artisans[$count]['short-description'] = trimReplace($child->nodeValue);
					}
					
				}
				$count++;
			}
		}
		
		$result['artisans'] = $artisans;
		
		$count = 0;
		$links = $dom->getElementsByTagName('a');
		for ($x = 0; $x <= $links->length - 1; $x++)
		{
			$object = $links->item($x);
			$object_href = trimReplace($object->getAttribute('href'));
			$object_class = trimReplace($object->getAttribute('class'));
			if (preg_match('~^/d3/'.$locale.'/artisan/.+~',$object_href) && $object_class == '')
			{
				$artisanUrl = $base_url.$object_href;
				if (shouldVisitUrl($artisanUrl))
				{
					getArtisanDetails($artisanUrl, $count);
					$count++;
				}
			}
		}
	}
}

function getArtisanDetails($url, $index)
{
	global $result;		
	global $base_url;
	global $region;
	global $locale;
	
	$dom = new DOMDocument('1.0');
	@$dom->loadHTMLFile($url);
	
	$artisan = $result['artisans'][$index];
	
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
				if (!isset($artisan['description']))
				{
					$artisan['description'] = '';
				}
				$artisan['description'] .= ' '.trimReplace($child->nodeValue);
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
					
					$artisan['icon'] = $val;
					
					$directory = 'D:\\Dropbox\\Projects\\Diablo 3 Site Crawler\\Images\\';
					
					saveFile($directory.fixFilename($filename),$image_url);
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
						$filename = strtolower($artisan['name']).'-'.$matches[1][0].$matches[2][0];
						$val = strtolower($artisan['name']).'-'.$matches[1][0];
						$features[$count]['icon'] = $val;
						
						$directory = 'D:\\Dropbox\\Projects\\Diablo 3 Site Crawler\\Images\\';
						
						saveFile($directory.fixFilename($filename),$base_url.$image_url);
					}
				}
				$count++;
			}
			$artisan['features'] = $features;
		}
	}
	
	$result['artisans'][$index] = $artisan;
	
	$links = $dom->getElementsByTagName('a');
	for ($x = 0; $x <= $links->length - 1; $x++)
	{
		$object = $links->item($x);
		$object_href = trimReplace($object->getAttribute('href'));
		$object_class = trimReplace($object->getAttribute('class'));
		if (preg_match('~^/d3/'.$locale.'/artisan/.*/recipe/$~',$object_href) && $object_class == '')
		{
			$recipeUrl = $base_url.$object_href;
			if (shouldVisitUrl($recipeUrl))
			{
				getArtisanRecipes($recipeUrl, $index);
			}
		}
	}
}

function getArtisanRecipes($url, $index)
{	
	global $base_url;
	global $locale;
	
	preg_match_all('~(http://.*/'.$locale.'/artisan/.*/recipe/)[#page=(.*)]?~',$url, $matches);
	$current_page = $matches[1][0];
	if (isset($matches[2][0]))
	{
		$page_number = $matches[2][0];
	}
	else
	{
		$page_number = 1;
	}
	
	$dom = new DOMDocument('1.0');
	@$dom->loadHTMLFile($url);
	
	$links = $dom->getElementsByTagName('a');
	for ($x = 0; $x <= $links->length - 1; $x++)
	{
		$object = $links->item($x);
		$object_href = trimReplace($object->getAttribute('href'));
		$object_class = trimReplace($object->getAttribute('class'));
		if (preg_match('~^/d3/'.$locale.'/artisan/.*/recipe/[^\?].+$~',$object_href) && ($object_class == 'hover-main d3-color-blue' || $object_class == 'hover-main d3-color-white'))
		{
			$recipeUrl = $base_url.$object_href;
			if (shouldVisitUrl($recipeUrl))
			{
				getArtisanRecipe($recipeUrl, $index);
			}
		}
		else if (preg_match_all('~#page=(.*)~',$object_href, $matches))
		{
			if ($page_number < $matches[1][0])
			{
				if (shouldVisitUrl($current_page.$object_href))
				{
					getArtisanRecipes($current_page.$object_href, $index);
				}
			
			}
		}
	}
}

function getArtisanRecipe($url, $index)
{
	global $result;		
	global $base_url;
	global $region;
	global $locale;
	
	$dom = new DOMDocument('1.0');
	@$dom->loadHTMLFile($url);
	
	$recipe = array();
	
	$meta = $dom->getElementsByTagName('meta');
	for ($x = 0; $x <= $meta->length - 1; $x++)
	{
		$object = $meta->item($x);
		$object_name = trimReplace($object->getAttribute('name'));
		if ($object_name == 'title')
		{
			$recipe['name'] =  trimReplace($object->getAttribute('content'));
		}
	}
	
	$span = $dom->getElementsByTagName('span');
	for ($x = 0; $x <= $span->length -1; $x++)
	{
		$object = $span->item($x);
		$object_value = trimReplace($object->nodeValue);
		$object_class = trimReplace($object->getAttribute('class'));
		if ($object_class == 'detail-level-number')
		{
			$recipe['required-level'] =  $object_value;
		}
		else if ($object_class == 'rank')
		{
			if (preg_match_all('~Level ([0-9])+ \((.*)\)~',$object_value, $matches))
			{
				if (isset($matches[1][0]))
				{
					$recipe['training-level'] = $matches[1][0];
				}
				if (isset($matches[2][0]))
				{
					$recipe['training-name'] = $matches[2][0];
				}
			}
		}
		else if ($object_class == 'cost')
		{
			$recipe['cost'] = str_replace(',', '', $object_value);
		}
	}
	
	$materials = array();
	
	$div = $dom->getElementsByTagName('div');
	for ($x = 0; $x <= $div->length -1; $x++)
	{
		$object = $div->item($x);
		$object_value = trimReplace($object->nodeValue);
		$object_class = trimReplace($object->getAttribute('class'));
		if ($object_class == 'recipe-materials')
		{
			$count = 0;
			$a = $object->getElementsByTagName('a');
			for ($y = 0; $y <= $a->length - 1; $y++)
			{
				$child = $a->item($y);
				$child_href = trimReplace($child->getAttribute('href'));
				$child_class = trimReplace($child->getAttribute('class'));
				if (preg_match_all('~d3/'.$locale.'/item/(.*)$~',$child_href, $matches))
				{
					$materials[$count]['name'] = $matches[1][0];
					$span = $child->getElementsByTagName('span');
					for ($z = 0; $z <= $span->length - 1; $z++)
					{
						$obj = $span->item($z);
						$obj_value = trimReplace($obj->nodeValue);
						$obj_class = trimReplace($obj->getAttribute('class'));
						if ($obj_class == 'no')
						{
							$materials[$count]['count'] = $obj_value;
						}
					}
					$count++;
				}
			}
		}
		else if ($object_class == 'page-section item-salvage colors-subtle')
		{
			$salvages = array();
			$count = 0;
			$a = $object->getElementsByTagName('a');
			for ($y = 0; $y <= $a->length - 1; $y++)
			{
				$child = $a->item($y);
				$child_value = trimReplace($child->nodeValue);
				$child_href = trimReplace($child->getAttribute('href'));
				$child_class = trimReplace($child->getAttribute('class'));
				if (preg_match_all('~d3/'.$locale.'/item/(.*)$~',$child_href, $matches))
				{
					$salvages[$count]['name'] = $matches[1][0];
					$span = $child->getElementsByTagName('span');
					for ($z = 0; $z <= $span->length - 1; $z++)
					{
						$obj = $span->item($z);
						$obj_value = trimReplace($obj->nodeValue);
						$obj_class = trimReplace($obj->getAttribute('class'));
						if ($obj_class == 'no')
						{
							$salvages[$count]['count'] = $obj_value;
						}
					}
					$count++;
				}
			}
			$recipe['salvages'] = $salvages;
		}
	}
	$recipe['materials'] = $materials;
	
	$ul = $dom->getElementsByTagName('ul');
	for ($x = 0; $x <= $ul->length - 1; $x++)
	{
		$object = $ul->item($x);
		$object_class = trimReplace($object->getAttribute('class'));
		if ($object_class == 'item-type-right')
		{
			$li = $object->getElementsByTagName('li');
			for ($y = 0; $y <= $li->length - 1; $y++)
			{
				$child = $li->item($y);
				$child_value = trimReplace($child->nodeValue);
				$child_class = trimReplace($child->getAttribute('class'));
				
				if ($child_class == 'item-slot')
				{
					$recipe['item-slot'] = $child_value;
				}
				else if ($child_class == 'item-class-specific')
				{
					if (preg_match_all('~^(Demon Hunter|Monk|Wizard|Witch Doctor|Barbarian).*$~', $child_value, $matches))
					{
						if (isset($matches[1][0]))
						{
							$recipe['class-specific'] = true;
							$recipe['class'] = $matches[1][0];
						}
					}
				}
			}
		}
		else if ($object_class == 'item-type')
		{
			$li = $object->getElementsByTagName('li');
			for ($y = 0; $y <= $li->length - 1; $y++)
			{
				$child = $li->item($y);
				$child_value = trimReplace($child->nodeValue);
				$child_class = trimReplace($child->getAttribute('class'));
				
				if (preg_match_all('~(.*) (.*)~',$child_value, $matches))
				{
					if (isset($matches[1][0]))
					{
						$recipe['rarity'] = $matches[1][0];
					}
					if (isset($matches[2][0]))
					{
						$recipe['item-type'] = $matches[2][0];
					}
				}
			}
		}
		else if ($object_class == 'item-armor-weapon')
		{
			$li = $object->getElementsByTagName('li');
			for ($y = 0; $y <= $li->length - 1; $y++)
			{
				$child = $li->item($y);
				$child_value = trimReplace($child->nodeValue);
				$child_class = trimReplace($child->getAttribute('class'));
				
				if ($child_class == 'big')
				{
					if (preg_match_all('~^([0-9]*\.?[0-9]*)[^0-9\.a-zA-Z]{0,6}?([0-9]\.?[0-9]*)? (.*)$~',$child_value, $matches))
					{
						if ($matches[3][0] == 'Damage Per Second')
						{
							if (isset($matches[1][0]))
							{
								$recipe['damage-per-second'] = $matches[1][0];
							}
						}
						else if ($matches[3][0] == 'Armor')
						{
							if (isset($matches[1][0]))
							{
								$recipe['armor-value-low'] = $matches[1][0];
							}
							if (isset($matches[2][0]))
							{
								$recipe['armor-value-high'] = $matches[2][0];
							}
						}
					}
				}
				else if ($child_class == '')
				{
					if (preg_match_all('~^([0-9]*\.?[0-9]*)[^0-9\.a-zA-Z]{0,6}?([0-9]\.?[0-9]*)? (.*)$~',$child_value, $matches))
					{
						if ($matches[3][0] == 'Damage')
						{
							if (isset($matches[1][0]))
							{
								if (isset($matches[2][0]))
								{
									$recipe['damage-low'] = $matches[1][0];
									$recipe['damage-high'] = $matches[2][0];
								}
								else
								{
									$recipe['damage'] = $matches[1][0];
								}
							}
						}
						else if ($matches[3][0] == 'Attacks per Second')
						{
							if (isset($matches[1][0]))
							{
								$recipe['attacks-per-second'] = $matches[1][0];
							}
						}
					}
				}
			}
		}
		else if ($object_class == 'item-effects')
		{
			$effects = array();
			$count = 0;
			$li = $object->getElementsByTagName('li');
			for ($y = 0; $y <= $li->length - 1; $y++)
			{
				$child = $li->item($y);
				$child_value = trimReplace($child->nodeValue);
				$child_class = trimReplace($child->getAttribute('class'));
				
				$effects[$count] = $child_value;
				$count++;
			}
			$recipe['effects'] = $effects;
		}
		else if ($object_class =='item-extras')
		{
			$li = $object->getElementsByTagName('li');
			for ($y = 0; $y <= $li->length - 1; $y++)
			{
				$child = $li->item($y);
				$child_value = trimReplace($child->nodeValue);
				$child_class = trimReplace($child->getAttribute('class'));
				
				if (preg_match_all('~^(.*): ([0-9]*\.?[0-9]*)[^0-9\.a-zA-Z]{0,6}?([0-9]\.?[0-9]*)?$~',$child_value, $matches))
				{
					if ($matches[1][0] == 'Durability')
					{
						if (isset($matches[2][0]))
						{
							if (isset($matches[3][0]))
							{
								$recipe['durability-low'] = $matches[2][0];
								$recipe['durability-high'] = $matches[3][0];
							}
							else
							{
								$recipe['durability'] = $matches[2][0];
							}
						}
					}
				}
			}
		}
	}
	
	/*
	$div = $dom->getElementsByTagName('div');
	for ($x = 0; $x <= $div->length -1; $x++)
	{
		$object = $div->item($x);
		$object_class = trimReplace($object->getAttribute('class'));
		if ($object_class == 'recipe-desc')
		{
			$recipe['cost'] = 0;
			$recipe['cost-units'] = '';
			$recipe['cost-description'] = '';
			$recipe['cooldown'] = 0;
			$recipe['cooldown-units'] = '';
			$recipe['cooldown-description'] = '';
			$recipe['generate'] = 0;
			$recipe['generate-units'] = '';
			$recipe['generate-description'] = '';
			$recipe['signature-spell'] = false;
			$recipe['description'] = '';
			
			for ($y = 0; $y <= $object->childNodes->length - 1; $y++)
			{
				$child = $object->childNodes->item($y);
				$child_value = trimReplace($child->nodeValue);
				if (preg_match_all('~(Generate|Cost|Cooldown):.?([0-9]*).?(Fury|Hatred|Discipline|Spirit|Mana|seconds).?(per attack|.*)?~', $child_value, $matches))
				{
					if (strtolower(trimReplace($matches[1][0])) == 'generate')
					{
						$recipe['cooldown'] = trimReplace($matches[2][0]);
						$recipe['cooldown-units'] = trimReplace($matches[3][0]);
						if (isset($matches[4][0]))
						{
							$recipe['cost-description'] = trimReplace($matches[4][0]);
						}
					}
					else if (strtolower(trimReplace($matches[1][0])) == 'cost')
					{
						$recipe['generate'] = trimReplace($matches[2][0]);
						$recipe['generate-units'] = trimReplace($matches[3][0]);
						if (isset($matches[4][0]))
						{
							$recipe['generate-description'] = trimReplace($matches[4][0]);
						}
					}
					else if (strtolower(trimReplace($matches[1][0])) == 'cooldown')
					{
						$recipe['cooldown'] = trimReplace($matches[2][0]);
						$recipe['cooldown-units'] = trimReplace($matches[3][0]);
						if (isset($matches[4][0]))
						{
							$recipe['cooldown-description'] = trimReplace($matches[4][0]);
						}
					}
					else
					{
						$recipe['description'] .= ' '.$child_value;
					}
				}
				else if (preg_match('~.*?Signature spell.*?~', $child_value))
				{
					$recipe['signature-spell'] = true;
				}
				else
				{
					$recipe['description'] .= ' '.$child_value;
				}
			}
		}
		else if ($object_class == 'db-detail-box colors-subtle recipe-detail' || $object_class == 'db-detail-box colors-subtle recipe-detail has-recipe-video')
		{
			$spans = $object->getElementsByTagName('span');
			for ($y = 0; $y <= $spans->length - 1; $y++)
			{
				$span = $spans->item($y);
				$span_class = trimReplace($span->getAttribute('class'));
				if ($span_class == 'd3-icon d3-icon-recipe d3-icon-recipe-64' || $span_class == 'd3-icon d3-icon-trait  d3-icon-trait-64')
				{
					preg_match_all('~.*url\(\'(.*)\'\)~', $span->getAttribute('style'), $matches);
					$image_url = $matches[1][0];
					preg_match_all('~^.* /(.*)(\..*)$~', $image_url, $matches);
					$filename = $matches[1][0].$matches[2][0];
					$val = $matches[1][0];
					$icon = $val;
					$values = explode($val, '.');
					$directory = 'D:\\Dropbox\\Projects\\Diablo 3 Site Crawler\\Skills\\Icons\\';

					if (!file_exists($directory.'64\\'.$filename))
					{
						file_put_contents($directory.'64\\'.$filename, file_get_contents($image_url));
					}
				}
			}
		}
	}*/
	
	$artisan = $result['artisans'][$index];
	$recipes = array();
	if (isset($artisan['recipes']))
	{
		$recipes = $artisan['recipes'];
	}
	
	array_push($recipes, $recipe);
	
	$artisan['recipes'] = $recipes;
	
	$result['artisans'][$index] = $artisan;
}
?>