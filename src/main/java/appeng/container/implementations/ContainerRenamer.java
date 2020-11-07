package appeng.container.implementations;

import appeng.api.config.SecurityPermissions;
import appeng.api.parts.IPart;
import appeng.container.AEBaseContainer;
import appeng.helpers.ICustomNameObject;
import appeng.util.Platform;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerRenamer extends AEBaseContainer
{
    private final ICustomNameObject namedObject;

    @SideOnly( Side.CLIENT )
    private GuiTextField textField;

    public ContainerRenamer(InventoryPlayer ip, ICustomNameObject obj)
    {
        super(ip, obj instanceof TileEntity ? (TileEntity)obj : null, obj instanceof IPart ? (IPart)obj : null);
        namedObject = obj;
    }

    @SideOnly( Side.CLIENT )
    public void setTextField( final GuiTextField name )
    {
        this.textField = name;
        if (getCustomName() != null)
            textField.setText(getCustomName());
    }

    public void setNewName(String newValue)
    {
        this.namedObject.setCustomName( newValue );
    }

    @Override
    public void setCustomName( final String customName )
    {
        super.setCustomName(customName);
        if (!Platform.isServer() && customName != null)
            textField.setText(customName);
    }

    @Override
    public void detectAndSendChanges()
    {
        verifyPermissions( SecurityPermissions.BUILD, false );
        super.detectAndSendChanges();
        if (!Platform.isServer() && getCustomName() != null)
            textField.setText(getCustomName());
    }
}
