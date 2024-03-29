# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/) and this project adheres to [Forge Recommended Versioning](https://mcforge.readthedocs.io/en/latest/conventions/versioning/).

## [1.20.4-5.0.0.0] - 2024-03-15
### Changed
- updated to Minecraft 1.20.4 (Forge 49.0.31, Neoforge 20.4.200)
- updated compat with Curios to 1.20.4-7.3.0+ (Forge & Neoforge)
- updated compat with Lucent to 1.20.4-1.6.0+ (Neoforge)

### Removed
- config option "isCuriosEnabled" (always enabled)

## [1.20.1-4.2.0.0] - 2023-08-20
### Changed
- Re-added Lucent 1.20.1-1.5.3 support #50

## [1.20.1-4.1.0.0] - 2023-08-09
### Changed
- Changed Forge to NeoForge 1.20.1-47.1.54 (compatible with Forge 47.1.0)

## [1.20.1-4.0.0.0] - 2023-06-14
### Changed
- Update mod to Forge 1.20.1-47.0.1 #49
- Update compat with Curios to 1.20.1-5.2.0-beta.3 #49
- Temporary deactivate Lucent support until it is ported to 1.20

## [1.19.4-3.2.0.0] - 2023-03-25
### Changed
- Update mod to Forge 1.19.4-45.0.24
- Update compat with Curios to 1.19.4-5.1.4.2 and Lucent to 1.19.4-v1.5.2

## [1.19.3-3.1.0.0] - 2022-12-31
### Changed
- Update mod to Forge 1.19.3-44.0.41
- Changed recipes to use more forge tags
- Update compat with Curios to 1.19.3-5.1.1.2 and Lucent to 1.19.3-v1.5.0

## [1.19.2-3.0.2.0] - 2022-10-05
### Added
- Lucent mod support for Mining Hat (if Lucent is installed, the night vision effect is replaced by the Lucent light effect)

### Changed
- optimize Mining Hat damaging to be more precisely

## [1.19.2-3.0.1.0] - 2022-09-13
### Changed
- Update mod to Forge 1.19.2-43.1.23
- Make Halo related to the new boss tag of Forge
- optimize Halo damaging to be more precise

### Removed
- removed Halo detecting range config option, because it is not needed any longer

## [1.19-3.0.0.0] - 2022-07-18
### Changed
- Update mod to Forge 1.19-41.0.96

### Removed
- Support for Baubles Reborn mod (seems to be discontinued)

## [1.18.2-2.0.2.0] - 2022-03-03
### Changed
- Update mod to Forge 1.18.2-40.0.2

## [1.18.1-2.0.1.0] - 2021-12-24
### Changed
- Update mod to Forge 1.18.1-39.0.0
- Moved curiosAddHeadSlot option to common config file to fix #43 (thanks to ImKatsun)

## [1.18-2.0.0.0] - 2021-12-10
### Changed
- Update mod to Forge 1.18-38.0.17 (fix Log4J security issue)
- Moved mod settings file to server (defaultconfigs directory or serverconfig directory of savegame)
- removed settings to enable/disable single hats, because it does not work with server config (could be done by datapack or CraftTweaker)

## [1.17.1-1.10.0.1] - 2021-12-07
### Fixed
- Curios rendering fixed
- Ender Helmet couldn't be placed in curio slot

## [1.17.1-1.10.0.0] - 2021-12-07
### Changed
- Update mod to Forge 1.17.1-37.0.48

## [1.16.5-1.10.0.0] - 2021-09-03
### Added
- Russian and Ukrainian translation (thanks to vstannumdum aka DMHYT) #42

### Changed
- Update mod to Forge 1.16.5-36.1.0
- changed versioning to fit [Forge Recommended Versioning](https://mcforge.readthedocs.io/en/latest/conventions/versioning/)

## [1.9.5_1.16] - 2021-04-30
### Fixed
- fixed startup issue #41 if Curios and Forge 36.1.10 (or later) are installed (thanks to azaquaz & bappitybup for the bugreport)

## [1.9.4_1.16] - 2021-04-13
### Changed
- change rendering of UsefulHatsModel to fix bug #40 (thanks to Yourname942 for the report)
- Hint: Aquanaut Helmet transparency effect is gone #6

## [1.9.3_1.16] - 2020-12-08
### Changed
- Baubles Reborn support updated (if Baubles is used, version 1.8.3 or later is required)

### Hints
- Don't forget the christmas variant of the stocking cap in december! :)
- Have a wonderful and healthy christmas time!

## [1.9.2_1.16] - 2020-12-01
### Added
- mod specific creative tab added

## [1.9.1_1.16] - 2020-11-24
### Added
- Add christmas variant for stocking cap (shown in december)
- support of enchantments of other mods
- Brazilian Portuguese translation updated (thanks to Mikeliro)

### Changed
- Clarify tooltip of Ender Helmet
- Unbreaking and Mending enchantments are disabled if damaging is disabled

### Fixed
- Book enchanting didn't work correctly

## [1.9.0_1.16] - 2020-11-10
### Added
- Ender Helmet added
- you need 5 Ender Pearls to craft an Ender Helmet
- shift right click with Ender Helmet in hand to define a teleport position
- when on head with a defined position: use an Ender Pearl to teleport to the defined position
- interdimensional traveling is possible (configurable)

## [1.8.5_1.16] - 2020-09-28
### Changed
- Minecraft 1.16.2 or later is required
- Forge 33.0.5 or later is required

### Fixed
- Aquanaut Overlay was not rendered when equipped in Curios or Baubles slot
- since Minecraft 1.16.2 the tooltips were outside the screen (thanks to Zygus42 for reporting)
- related crashes of previous version 1.8.4 are fixed
- Due to that Minecraft 1.16.1 is not supported any longer

## [1.8.4_1.16] - 2020-09-23
### Added
- Curios head slot can now be added with the config option "curiosAddHeadSlot" (default:disabled)

### Fixed
- bugfix: since Minecraft 1.16.2 the tooltips were outside the screen (thanks to Zygus42 for reporting)
- EDIT: The bugfix causes crashes in 1.16.2+. So, it is only compatible with 1.16.1.

## [1.8.3_1.16] - 2020-09-15
### Added
- wearing a Mining Hat makes Piglins neutral (configurable)
- added support for Baubles Reborn

## [1.8.2_1.16] - 2020-09-12
### Fixed
- bugfix: Chopping Hat effect didn't work in 1.16 because of a bug in Forge - fixed with a workaround

## [1.8.1_1.16] - 2020-09-10
### Added
- zh_tw translation added (thanks to pancakes0228)

### Changed
- Wing Helmet effect is now active only when falling (not when (elytra) flying or jumping up)
- Mining Hat, Chopping Hat, Straw Hat effects are now compatible with Silent Gear tools
- pt_br translation updated (thanks to Mikeliro)

### Fixed
- bugfix: wearing more than one same hat, effects are stacking and/or damage is taken for both

## [1.8.0_1.16] - 2020-09-03
### Added
- Bunny Ears added - gives Jump Boost & lets you eat faster (thanks to Jonatan javier for the idea)
- Mushroom Hat added - take a bite out of this hat automatically (thanks to AbsorbingSafe11 for the idea)
- Shulker Helmet added - gives Levitation (thanks to AbsorbingSafe11 for the idea)
- Lucky Hat is higher now (like a top hat)

### Changed
- Hats are now rendered when equipped in Curios slot
- Other Curios slots are supported if the hats are added to them via data packs
- Support other mods that are changing the break speed via PlayerEvent.BreakSpeed

### Fixed
- Some hats had not glint effect when they were enchanted




