/**
 * 
 */
package za.co.sindi.cic.rest.resource;

import java.util.logging.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import za.co.sindi.cic.rest.entity.ExceptionEntity;
import za.co.sindi.cic.rest.util.Throwables;
import za.co.sindi.cic.service.exception.ServiceException;

/**
 * @author buhake.sindi
 * @since 2015/08/07
 *
 */
public abstract class AbstractResource {
	
	protected final Logger LOGGER = Logger.getLogger(this.getClass().getName());

	protected WebApplicationException toWebApplicationException(RuntimeException exception) {
		if (exception instanceof WebApplicationException) {
			return (WebApplicationException)exception;
		}
		
		return new WebApplicationException(Response.status(Status.BAD_REQUEST).entity(new ExceptionEntity(exception)).build());
	}
	
	protected WebApplicationException toWebApplicationException(ServiceException exception) {
		Status status = Status.INTERNAL_SERVER_ERROR;
		Throwable rootCause = Throwables.getRootCause(exception);
		if (rootCause != null && rootCause instanceof RuntimeException) {
			status = Status.BAD_REQUEST;
		}
		
		return new WebApplicationException(Response.status(status).entity(new ExceptionEntity(exception)).build());
	}
}
