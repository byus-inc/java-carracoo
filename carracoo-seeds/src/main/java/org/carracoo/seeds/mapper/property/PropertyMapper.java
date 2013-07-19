/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carracoo.seeds.mapper.property;

import org.carracoo.seeds.SeedMapper;
import org.carracoo.seeds.SeedView;

/**
 *
 * @author Sergey
 */
public class PropertyMapper implements SeedMapper {

	@Override
	public Object encode(SeedView view, Object object) {
		return new PropertyEncoder().encode(view, object);
	}

	@Override
	public <T> T decode(SeedView view, Object value, Class<T> type) {
		return new PropertyDecoder().decode(view, value, type);
	}
	
}
