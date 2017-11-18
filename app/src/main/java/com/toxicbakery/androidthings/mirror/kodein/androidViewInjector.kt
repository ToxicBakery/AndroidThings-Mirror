package com.toxicbakery.androidthings.mirror.kodein

import android.content.Context
import android.view.View
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.android.AndroidInjector
import com.github.salomonbrys.kodein.android.AndroidScope
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.bindings.InstanceBinding
import com.github.salomonbrys.kodein.bindings.ScopeRegistry
import com.github.salomonbrys.kodein.erased
import java.util.*

interface AndroidViewInjector : AndroidInjector<View, AndroidScope<View>> {
    override val kodeinScope: AndroidScope<View> get() = androidViewScope

    override fun initializeInjector() {
        val viewModule = Kodein.Module {
            Bind<KodeinInjected>(erased()) with InstanceBinding(erased(), this@AndroidViewInjector)
            Bind<Context>(erased()) with InstanceBinding(erased(), kodeinComponent.context)
            Bind<View>(erased()) with InstanceBinding(erased(), kodeinComponent)

            import(provideOverridingModule(), allowOverride = true)
        }

        inject(Kodein {
            extend(kodeinComponent.appKodein(), allowOverride = true)
            import(viewModule, allowOverride = true)
        })
    }
}

object androidViewScope : AndroidScope<View> {

    /**
     * Map that associates a ScopeRegistry to a view.
     *
     * Because it's a weak hash map, this does not prevent the view from being destroyed.
     */
    private val viewScopes = WeakHashMap<View, ScopeRegistry>()

    /**
     * Get a registry for a given view. Will always return the same registry for the same view.
     *
     * @param context The view associated with the returned registry.
     * @return The registry associated with the given view.
     */
    override fun getRegistry(context: View): ScopeRegistry
            = synchronized(viewScopes) { viewScopes.getOrPut(context) { ScopeRegistry() } }

    /**
     * Allows for cleaning up after a view has been destroyed
     */
    override fun removeFromScope(context: View) = viewScopes.remove(context)

}