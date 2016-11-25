package cn.mapway.document.ui.client.resource;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Tree.Resources;

// TODO: Auto-generated Javadoc
/**
 * The Class TreeResource.
 */
public class TreeResource implements Resources {

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Tree.Resources#treeClosed()
	 */
	@Override
	public ImageResource treeClosed() {

		return SysResource.INSTANCE.treeClose();
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Tree.Resources#treeLeaf()
	 */
	@Override
	public ImageResource treeLeaf() {

		return SysResource.INSTANCE.inter();
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Tree.Resources#treeOpen()
	 */
	@Override
	public ImageResource treeOpen() {

		return SysResource.INSTANCE.treeOpen();
	}

}
