package com.mthwate.dominion.common;

import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mthwate
 */
public class ListLoader implements AssetLoader {

	@Override
	public List<String> load(AssetInfo assetInfo) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(assetInfo.openStream()));

		List<String> list = new ArrayList<String>();
		
		String line;
		while ((line = br.readLine()) != null) {
			list.add(line);
		}
		br.close();

		return list;
	}
}
