package cn.mapway.document.ui.client.resource;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Tree.Resources;

public class TreeResource implements Resources {

	@Override
	public ImageResource treeClosed() {

		return SysResource.INSTANCE.treeClose();
	}

	@Override
	public ImageResource treeLeaf() {

		return SysResource.INSTANCE.inter();
	}

	@Override
	public ImageResource treeOpen() {

		return SysResource.INSTANCE.treeOpen();
	}

}
