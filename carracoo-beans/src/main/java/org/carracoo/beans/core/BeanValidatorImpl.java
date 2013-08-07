package org.carracoo.beans.core;

import org.carracoo.beans.*;
import org.carracoo.beans.exceptions.BeanException;
import org.carracoo.beans.exceptions.BeanValidationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/25/13
 * Time: 4:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class BeanValidatorImpl implements BeanValidator {


	private final BeanService service;
	private final Walker walker;

	public BeanValidatorImpl(){
		this(new BeanServiceImpl());
	}

	public BeanValidatorImpl(BeanService service){
		this(service,new WalkerImpl());
	}

	public BeanValidatorImpl(BeanService service, Walker walker){
		this.service = service;
		this.walker  = walker;
	}

	private Walker initialize(Walker walker){
		return walker;
	}

	@Override
	public BeanService getService() {
		return service;
	}

	@Override
	public void validate(Object bean) throws BeanValidationException {
		validate(walker,bean);
	}

	@Override
	public void validate(Walker view, Object bean) throws BeanValidationException {
		if(bean!=null && service.isBean(bean.getClass())){
			List<BeanValidationException.Error> errors = new ArrayList<BeanValidationException.Error>();
			validateBean(errors,view.clone(),bean);
			if(errors.size()>0){
				throw new BeanValidationException(errors);
			}
		}
	}


	public void validateBean(List<BeanValidationException.Error> errors, Walker view, Object bean){
		try {
			if(bean==null){
				return;
			}
			for(Property property:service.getProperties(bean)){
				view.enter(property.options.name);
				View.ValueValidator validator = null;
				Boolean isBean = service.isBean(property.options.type);
				if(property.options instanceof View.ValueValidator){
					validator = ((View.ValueValidator)property.options);
				}
				if(property.empty()){
					try {
						if(validator!=null){
							validator.validate(view,property,-1);
						}
					}catch (BeanValidationException ex){
						errors.addAll(ex.getErrors());
					}
				}else{
					if(property.options instanceof View.ValueValidator){
						if(property.options.multiple){
							for(int i=0;i<property.all().length;i++){
								view.enter(i);
								try {
									validator.validate(view,property,i);
								}catch (BeanValidationException ex){
									errors.addAll(ex.getErrors());
								}
								if(isBean){
									validateBean(errors,view,property.get(i));
								}
								view.exit();
							}
						}else{
							try {
								validator.validate(view,property,0);
							}catch (BeanValidationException ex){
								errors.addAll(ex.getErrors());
							}
							if(isBean){
								validateBean(errors,view,property.get(0));
							}
						}
					}
				}
				view.exit();
			}
			if(bean instanceof View.BeanValidator){
				((View.BeanValidator)bean).validate(view);
			}
		}catch (BeanException e){
			errors.add(
				BeanValidationException.Error.get(
					view,
					"INVALID_BEAN_OBJECT",
					"cant initialize properties of bean"
				)
			);
		}
	}
}
