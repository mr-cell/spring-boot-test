package mr.cell.incubator.springboottest.resource;

import org.springframework.hateoas.core.DefaultRelProvider;
import org.springframework.stereotype.Component;

@Component
public class CustomRelProvider extends DefaultRelProvider {

	@Override
	public String getCollectionResourceRelFor(Class<?> type) {
		if(type == BookmarkResource.class) {
			return "bookmarks";
		} else if (type == PersonResource.class) {
			return "persons";
		} else {
			return super.getCollectionResourceRelFor(type);
		}
	}
}
