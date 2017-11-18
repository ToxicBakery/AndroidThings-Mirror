package com.toxicbakery.androidthings.mirror.kodein

import android.content.Context
import com.firebase.jobdispatcher.JobService
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.android.AndroidInjector
import com.github.salomonbrys.kodein.android.AndroidScope
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.bindings.InstanceBinding
import com.github.salomonbrys.kodein.bindings.ScopeRegistry
import com.github.salomonbrys.kodein.erased
import java.util.*

interface JobServiceInjector : AndroidInjector<JobService, AndroidScope<JobService>> {
    override val kodeinScope: AndroidScope<JobService> get() = jobServiceScope

    override fun initializeInjector() {
        val viewModule = Kodein.Module {
            Bind<KodeinInjected>(erased()) with InstanceBinding(erased(), this@JobServiceInjector)
            Bind<Context>(erased()) with InstanceBinding(erased(), kodeinComponent.applicationContext)

            import(provideOverridingModule(), allowOverride = true)
        }

        inject(Kodein {
            extend(kodeinComponent.appKodein(), allowOverride = true)
            import(viewModule, allowOverride = true)
        })
    }
}

object jobServiceScope : AndroidScope<JobService> {

    /**
     * Map that associates a ScopeRegistry to a view.
     *
     * Because it's a weak hash map, this does not prevent the view from being destroyed.
     */
    private val viewScopes = WeakHashMap<JobService, ScopeRegistry>()

    /**
     * Get a registry for a given view. Will always return the same registry for the same view.
     *
     * @param jobService The view associated with the returned registry.
     * @return The registry associated with the given view.
     */
    override fun getRegistry(jobService: JobService): ScopeRegistry
            = synchronized(viewScopes) { viewScopes.getOrPut(jobService) { ScopeRegistry() } }

    /**
     * Allows for cleaning up after a view has been destroyed
     */
    override fun removeFromScope(jobService: JobService) = viewScopes.remove(jobService)

}