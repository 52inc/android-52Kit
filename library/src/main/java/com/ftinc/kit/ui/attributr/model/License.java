/*
 * Copyright (c) 2015 52inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ftinc.kit.ui.attributr.model;

import java.util.IllegalFormatException;

/**
 * Project: 52Kit
 * Package: com.ftinc.kit.ui.attributr.model
 * Created by drew.heavner on 3/9/15.
 */
public enum License {
    APACHE2("apache",
            "Copyright %s %s\n" +
            "\n" +
            "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
            "you may not use this file except in compliance with the License.\n" +
            "You may obtain a copy of the License at\n" +
            "\n" +
            "    http://www.apache.org/licenses/LICENSE-2.0\n" +
            "\n" +
            "Unless required by applicable law or agreed to in writing, software\n" +
            "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
            "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
            "See the License for the specific language governing permissions and\n" +
            "limitations under the License."),

    MIT("mit",
        "The MIT License (MIT)\n" +
        "\n" +
        "Copyright (c) %s %s\n" +
        "\n" +
        "Permission is hereby granted, free of charge, to any person obtaining a copy\n" +
        "of this software and associated documentation files (the \"Software\"), to deal\n" +
        "in the Software without restriction, including without limitation the rights\n" +
        "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n" +
        "copies of the Software, and to permit persons to whom the Software is\n" +
        "furnished to do so, subject to the following conditions:\n" +
        "\n" +
        "The above copyright notice and this permission notice shall be included in all\n" +
        "copies or substantial portions of the Software.\n" +
        "\n" +
        "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" +
        "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" +
        "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" +
        "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" +
        "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" +
        "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\n" +
        "SOFTWARE."),

    AFFERO_GPL_V3(
            "a-gpl-v3",
            "%s\n" +
            "Copyright (C) %s  %s\n" +
            "\n" +
            "This program is free software: you can redistribute it and/or modify\n" +
            "it under the terms of the GNU Affero General Public License as published\n" +
            "by the Free Software Foundation, either version 3 of the License, or\n" +
            "(at your option) any later version.\n" +
            "\n" +
            "This program is distributed in the hope that it will be useful,\n" +
            "but WITHOUT ANY WARRANTY; without even the implied warranty of\n" +
            "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n" +
            "GNU Affero General Public License for more details.\n" +
            "\n" +
            "You should have received a copy of the GNU Affero General Public License\n" +
            "along with this program.  If not, see <http://www.gnu.org/licenses/>."),

    GPL_V2( "gpl-v2",
            "%s}\n" +
            "Copyright (C) %s  %s\n" +
            "\n" +
            "This program is free software; you can redistribute it and/or modify\n" +
            "it under the terms of the GNU General Public License as published by\n" +
            "the Free Software Foundation; either version 2 of the License, or\n" +
            "(at your option) any later version.\n" +
            "\n" +
            "This program is distributed in the hope that it will be useful,\n" +
            "but WITHOUT ANY WARRANTY; without even the implied warranty of\n" +
            "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n" +
            "GNU General Public License for more details.\n" +
            "\n" +
            "You should have received a copy of the GNU General Public License along\n" +
            "with this program; if not, write to the Free Software Foundation, Inc.,\n" +
            "51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA."),
    GPL_V3( "gpl-v3",
            "%s\n" +
            "Copyright (C) %s  %s\n" +
            "\n" +
            "This program is free software: you can redistribute it and/or modify\n" +
            "it under the terms of the GNU General Public License as published by\n" +
            "the Free Software Foundation, either version 3 of the License, or\n" +
            "(at your option) any later version.\n" +
            "\n" +
            "This program is distributed in the hope that it will be useful,\n" +
            "but WITHOUT ANY WARRANTY; without even the implied warranty of\n" +
            "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n" +
            "GNU General Public License for more details.\n" +
            "\n" +
            "You should have received a copy of the GNU General Public License\n" +
            "along with this program.  If not, see <http://www.gnu.org/licenses/>."
    ),

    ARTISTIC("artistic",
            "               The Artistic License 2.0\n" +
            "\n" +
            "           Copyright (c) %s %s\n" +
            "\n" +
            "     Everyone is permitted to copy and distribute verbatim copies\n" +
            "      of this license document, but changing it is not allowed.\n" +
            "\n" +
            "Preamble\n" +
            "\n" +
            "This license establishes the terms under which a given free software\n" +
            "Package may be copied, modified, distributed, and/or redistributed.\n" +
            "The intent is that the Copyright Holder maintains some artistic\n" +
            "control over the development of that Package while still keeping the\n" +
            "Package available as open url and free software.\n" +
            "\n" +
            "You are always permitted to make arrangements wholly outside of this\n" +
            "license directly with the Copyright Holder of a given Package.  If the\n" +
            "terms of this license do not permit the full use that you propose to\n" +
            "make of the Package, you should contact the Copyright Holder and seek\n" +
            "a different licensing arrangement.\n" +
            "\n" +
            "Definitions\n" +
            "\n" +
            "    \"Copyright Holder\" means the individual(s) or organization(s)\n" +
            "    named in the copyright notice for the entire Package.\n" +
            "\n" +
            "    \"Contributor\" means any party that has contributed code or other\n" +
            "    material to the Package, in accordance with the Copyright Holder's\n" +
            "    procedures.\n" +
            "\n" +
            "    \"You\" and \"your\" means any person who would like to copy,\n" +
            "    distribute, or modify the Package.\n" +
            "\n" +
            "    \"Package\" means the collection of files distributed by the\n" +
            "    Copyright Holder, and derivatives of that collection and/or of\n" +
            "    those files. A given Package may consist of either the Standard\n" +
            "    Version, or a Modified Version.\n" +
            "\n" +
            "    \"Distribute\" means providing a copy of the Package or making it\n" +
            "    accessible to anyone else, or in the case of a company or\n" +
            "    organization, to others outside of your company or organization.\n" +
            "\n" +
            "    \"Distributor Fee\" means any fee that you charge for Distributing\n" +
            "    this Package or providing support for this Package to another\n" +
            "    party.  It does not mean licensing fees.\n" +
            "\n" +
            "    \"Standard Version\" refers to the Package if it has not been\n" +
            "    modified, or has been modified only in ways explicitly requested\n" +
            "    by the Copyright Holder.\n" +
            "\n" +
            "    \"Modified Version\" means the Package, if it has been changed, and\n" +
            "    such changes were not explicitly requested by the Copyright\n" +
            "    Holder.\n" +
            "\n" +
            "    \"Original License\" means this Artistic License as Distributed with\n" +
            "    the Standard Version of the Package, in its current version or as\n" +
            "    it may be modified by The Perl Foundation in the future.\n" +
            "\n" +
            "    \"Source\" form means the url code, documentation url, and\n" +
            "    configuration files for the Package.\n" +
            "\n" +
            "    \"Compiled\" form means the compiled bytecode, object code, binary,\n" +
            "    or any other form resulting from mechanical transformation or\n" +
            "    translation of the Source form.\n" +
            "\n" +
            "\n" +
            "Permission for Use and Modification Without Distribution\n" +
            "\n" +
            "(1)  You are permitted to use the Standard Version and create and use\n" +
            "Modified Versions for any purpose without restriction, provided that\n" +
            "you do not Distribute the Modified Version.\n" +
            "\n" +
            "\n" +
            "Permissions for Redistribution of the Standard Version\n" +
            "\n" +
            "(2)  You may Distribute verbatim copies of the Source form of the\n" +
            "Standard Version of this Package in any medium without restriction,\n" +
            "either gratis or for a Distributor Fee, provided that you duplicate\n" +
            "all of the original copyright notices and associated disclaimers.  At\n" +
            "your discretion, such verbatim copies may or may not include a\n" +
            "Compiled form of the Package.\n" +
            "\n" +
            "(3)  You may apply any bug fixes, portability changes, and other\n" +
            "modifications made available from the Copyright Holder.  The resulting\n" +
            "Package will still be considered the Standard Version, and as such\n" +
            "will be subject to the Original License.\n" +
            "\n" +
            "\n" +
            "Distribution of Modified Versions of the Package as Source\n" +
            "\n" +
            "(4)  You may Distribute your Modified Version as Source (either gratis\n" +
            "or for a Distributor Fee, and with or without a Compiled form of the\n" +
            "Modified Version) provided that you clearly document how it differs\n" +
            "from the Standard Version, including, but not limited to, documenting\n" +
            "any non-standard features, executables, or modules, and provided that\n" +
            "you do at least ONE of the following:\n" +
            "\n" +
            "    (a)  make the Modified Version available to the Copyright Holder\n" +
            "    of the Standard Version, under the Original License, so that the\n" +
            "    Copyright Holder may include your modifications in the Standard\n" +
            "    Version.\n" +
            "\n" +
            "    (b)  ensure that installation of your Modified Version does not\n" +
            "    prevent the user installing or running the Standard Version. In\n" +
            "    addition, the Modified Version must bear a name that is different\n" +
            "    from the name of the Standard Version.\n" +
            "\n" +
            "    (c)  allow anyone who receives a copy of the Modified Version to\n" +
            "    make the Source form of the Modified Version available to others\n" +
            "    under\n" +
            "\n" +
            "    (i)  the Original License or\n" +
            "\n" +
            "    (ii)  a license that permits the licensee to freely copy,\n" +
            "    modify and redistribute the Modified Version using the same\n" +
            "    licensing terms that apply to the copy that the licensee\n" +
            "    received, and requires that the Source form of the Modified\n" +
            "    Version, and of any works derived from it, be made freely\n" +
            "    available in that license fees are prohibited but Distributor\n" +
            "    Fees are allowed.\n" +
            "\n" +
            "\n" +
            "Distribution of Compiled Forms of the Standard Version\n" +
            "or Modified Versions without the Source\n" +
            "\n" +
            "(5)  You may Distribute Compiled forms of the Standard Version without\n" +
            "the Source, provided that you include complete instructions on how to\n" +
            "get the Source of the Standard Version.  Such instructions must be\n" +
            "valid at the time of your distribution.  If these instructions, at any\n" +
            "time while you are carrying out such distribution, become invalid, you\n" +
            "must provide new instructions on demand or cease further distribution.\n" +
            "If you provide valid instructions or cease distribution within thirty\n" +
            "days after you become aware that the instructions are invalid, then\n" +
            "you do not forfeit any of your rights under this license.\n" +
            "\n" +
            "(6)  You may Distribute a Modified Version in Compiled form without\n" +
            "the Source, provided that you comply with Section 4 with respect to\n" +
            "the Source of the Modified Version.\n" +
            "\n" +
            "\n" +
            "Aggregating or Linking the Package\n" +
            "\n" +
            "(7)  You may aggregate the Package (either the Standard Version or\n" +
            "Modified Version) with other packages and Distribute the resulting\n" +
            "aggregation provided that you do not charge a licensing fee for the\n" +
            "Package.  Distributor Fees are permitted, and licensing fees for other\n" +
            "components in the aggregation are permitted. The terms of this license\n" +
            "apply to the use and Distribution of the Standard or Modified Versions\n" +
            "as included in the aggregation.\n" +
            "\n" +
            "(8) You are permitted to link Modified and Standard Versions with\n" +
            "other works, to embed the Package in a larger work of your own, or to\n" +
            "build stand-alone binary or bytecode versions of applications that\n" +
            "include the Package, and Distribute the result without restriction,\n" +
            "provided the result does not expose a direct interface to the Package.\n" +
            "\n" +
            "\n" +
            "Items That are Not Considered Part of a Modified Version\n" +
            "\n" +
            "(9) Works (including, but not limited to, modules and scripts) that\n" +
            "merely extend or make use of the Package, do not, by themselves, cause\n" +
            "the Package to be a Modified Version.  In addition, such works are not\n" +
            "considered parts of the Package itself, and are not subject to the\n" +
            "terms of this license.\n" +
            "\n" +
            "\n" +
            "General Provisions\n" +
            "\n" +
            "(10)  Any use, modification, and distribution of the Standard or\n" +
            "Modified Versions is governed by this Artistic License. By using,\n" +
            "modifying or distributing the Package, you accept this license. Do not\n" +
            "use, modify, or distribute the Package, if you do not accept this\n" +
            "license.\n" +
            "\n" +
            "(11)  If your Modified Version has been derived from a Modified\n" +
            "Version made by someone other than you, you are nevertheless required\n" +
            "to ensure that your Modified Version complies with the requirements of\n" +
            "this license.\n" +
            "\n" +
            "(12)  This license does not grant you the right to use any trademark,\n" +
            "service mark, tradename, or logo of the Copyright Holder.\n" +
            "\n" +
            "(13)  This license includes the non-exclusive, worldwide,\n" +
            "free-of-charge patent license to make, have made, use, offer to sell,\n" +
            "sell, import and otherwise transfer the Package with respect to any\n" +
            "patent claims licensable by the Copyright Holder that are necessarily\n" +
            "infringed by the Package. If you institute patent litigation\n" +
            "(including a cross-claim or counterclaim) against any party alleging\n" +
            "that the Package constitutes direct or contributory patent\n" +
            "infringement, then this Artistic License to you shall terminate on the\n" +
            "date that such litigation is filed.\n" +
            "\n" +
            "(14)  Disclaimer of Warranty:\n" +
            "THE PACKAGE IS PROVIDED BY THE COPYRIGHT HOLDER AND CONTRIBUTORS \"AS\n" +
            "IS' AND WITHOUT ANY EXPRESS OR IMPLIED WARRANTIES. THE IMPLIED\n" +
            "WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR\n" +
            "NON-INFRINGEMENT ARE DISCLAIMED TO THE EXTENT PERMITTED BY YOUR LOCAL\n" +
            "LAW. UNLESS REQUIRED BY LAW, NO COPYRIGHT HOLDER OR CONTRIBUTOR WILL\n" +
            "BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, OR CONSEQUENTIAL\n" +
            "DAMAGES ARISING IN ANY WAY OUT OF THE USE OF THE PACKAGE, EVEN IF\n" +
            "ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.\n"
    ),

    ECLIPSE("eclipse",
        "Eclipse Public License - v 1.0\n" +
        "\n" +
        "THE ACCOMPANYING PROGRAM IS PROVIDED UNDER THE TERMS OF THIS ECLIPSE PUBLIC\n" +
        "LICENSE (\"AGREEMENT\"). ANY USE, REPRODUCTION OR DISTRIBUTION OF THE PROGRAM\n" +
        "CONSTITUTES RECIPIENT'S ACCEPTANCE OF THIS AGREEMENT.\n" +
        "\n" +
        "1. DEFINITIONS\n" +
        "\n" +
        "\"Contribution\" means:\n" +
        "\n" +
        "a) in the case of the initial Contributor, the initial code and documentation\n" +
        "   distributed under this Agreement, and\n" +
        "b) in the case of each subsequent Contributor:\n" +
        "    i) changes to the Program, and\n" +
        "   ii) additions to the Program;\n" +
        "\n" +
        "   where such changes and/or additions to the Program originate from and are\n" +
        "   distributed by that particular Contributor. A Contribution 'originates'\n" +
        "   from a Contributor if it was added to the Program by such Contributor\n" +
        "   itself or anyone acting on such Contributor's behalf. Contributions do not\n" +
        "   include additions to the Program which: (i) are separate modules of\n" +
        "   software distributed in conjunction with the Program under their own\n" +
        "   license agreement, and (ii) are not derivative works of the Program.\n" +
        "\n" +
        "\"Contributor\" means any person or entity that distributes the Program.\n" +
        "\n" +
        "\"Licensed Patents\" mean patent claims licensable by a Contributor which are\n" +
        "necessarily infringed by the use or sale of its Contribution alone or when\n" +
        "combined with the Program.\n" +
        "\n" +
        "\"Program\" means the Contributions distributed in accordance with this\n" +
        "Agreement.\n" +
        "\n" +
        "\"Recipient\" means anyone who receives the Program under this Agreement,\n" +
        "including all Contributors.\n" +
        "\n" +
        "2. GRANT OF RIGHTS\n" +
        "  a) Subject to the terms of this Agreement, each Contributor hereby grants\n" +
        "     Recipient a non-exclusive, worldwide, royalty-free copyright license to\n" +
        "     reproduce, prepare derivative works of, publicly display, publicly\n" +
        "     perform, distribute and sublicense the Contribution of such Contributor,\n" +
        "     if any, and such derivative works, in url code and object code form.\n" +
        "  b) Subject to the terms of this Agreement, each Contributor hereby grants\n" +
        "     Recipient a non-exclusive, worldwide, royalty-free patent license under\n" +
        "     Licensed Patents to make, use, sell, offer to sell, import and otherwise\n" +
        "     transfer the Contribution of such Contributor, if any, in url code and\n" +
        "     object code form. This patent license shall apply to the combination of\n" +
        "     the Contribution and the Program if, at the time the Contribution is\n" +
        "     added by the Contributor, such addition of the Contribution causes such\n" +
        "     combination to be covered by the Licensed Patents. The patent license\n" +
        "     shall not apply to any other combinations which include the Contribution.\n" +
        "     No hardware per se is licensed hereunder.\n" +
        "  c) Recipient understands that although each Contributor grants the licenses\n" +
        "     to its Contributions set forth herein, no assurances are provided by any\n" +
        "     Contributor that the Program does not infringe the patent or other\n" +
        "     intellectual property rights of any other entity. Each Contributor\n" +
        "     disclaims any liability to Recipient for claims brought by any other\n" +
        "     entity based on infringement of intellectual property rights or\n" +
        "     otherwise. As a condition to exercising the rights and licenses granted\n" +
        "     hereunder, each Recipient hereby assumes sole responsibility to secure\n" +
        "     any other intellectual property rights needed, if any. For example, if a\n" +
        "     third party patent license is required to allow Recipient to distribute\n" +
        "     the Program, it is Recipient's responsibility to acquire that license\n" +
        "     before distributing the Program.\n" +
        "  d) Each Contributor represents that to its knowledge it has sufficient\n" +
        "     copyright rights in its Contribution, if any, to grant the copyright\n" +
        "     license set forth in this Agreement.\n" +
        "\n" +
        "3. REQUIREMENTS\n" +
        "\n" +
        "A Contributor may choose to distribute the Program in object code form under\n" +
        "its own license agreement, provided that:\n" +
        "\n" +
        "  a) it complies with the terms and conditions of this Agreement; and\n" +
        "  b) its license agreement:\n" +
        "      i) effectively disclaims on behalf of all Contributors all warranties\n" +
        "         and conditions, express and implied, including warranties or\n" +
        "         conditions of title and non-infringement, and implied warranties or\n" +
        "         conditions of merchantability and fitness for a particular purpose;\n" +
        "     ii) effectively excludes on behalf of all Contributors all liability for\n" +
        "         damages, including direct, indirect, special, incidental and\n" +
        "         consequential damages, such as lost profits;\n" +
        "    iii) states that any provisions which differ from this Agreement are\n" +
        "         offered by that Contributor alone and not by any other party; and\n" +
        "     iv) states that url code for the Program is available from such\n" +
        "         Contributor, and informs licensees how to obtain it in a reasonable\n" +
        "         manner on or through a medium customarily used for software exchange.\n" +
        "\n" +
        "When the Program is made available in url code form:\n" +
        "\n" +
        "  a) it must be made available under this Agreement; and\n" +
        "  b) a copy of this Agreement must be included with each copy of the Program.\n" +
        "     Contributors may not remove or alter any copyright notices contained\n" +
        "     within the Program.\n" +
        "\n" +
        "Each Contributor must identify itself as the originator of its Contribution,\n" +
        "if\n" +
        "any, in a manner that reasonably allows subsequent Recipients to identify the\n" +
        "originator of the Contribution.\n" +
        "\n" +
        "4. COMMERCIAL DISTRIBUTION\n" +
        "\n" +
        "Commercial distributors of software may accept certain responsibilities with\n" +
        "respect to end users, business partners and the like. While this license is\n" +
        "intended to facilitate the commercial use of the Program, the Contributor who\n" +
        "includes the Program in a commercial product offering should do so in a manner\n" +
        "which does not create potential liability for other Contributors. Therefore,\n" +
        "if a Contributor includes the Program in a commercial product offering, such\n" +
        "Contributor (\"Commercial Contributor\") hereby agrees to defend and indemnify\n" +
        "every other Contributor (\"Indemnified Contributor\") against any losses,\n" +
        "damages and costs (collectively \"Losses\") arising from claims, lawsuits and\n" +
        "other legal actions brought by a third party against the Indemnified\n" +
        "Contributor to the extent caused by the acts or omissions of such Commercial\n" +
        "Contributor in connection with its distribution of the Program in a commercial\n" +
        "product offering. The obligations in this section do not apply to any claims\n" +
        "or Losses relating to any actual or alleged intellectual property\n" +
        "infringement. In order to qualify, an Indemnified Contributor must:\n" +
        "a) promptly notify the Commercial Contributor in writing of such claim, and\n" +
        "b) allow the Commercial Contributor to control, and cooperate with the\n" +
        "Commercial Contributor in, the defense and any related settlement\n" +
        "negotiations. The Indemnified Contributor may participate in any such claim at\n" +
        "its own expense.\n" +
        "\n" +
        "For example, a Contributor might include the Program in a commercial product\n" +
        "offering, Product X. That Contributor is then a Commercial Contributor. If\n" +
        "that Commercial Contributor then makes performance claims, or offers\n" +
        "warranties related to Product X, those performance claims and warranties are\n" +
        "such Commercial Contributor's responsibility alone. Under this section, the\n" +
        "Commercial Contributor would have to defend claims against the other\n" +
        "Contributors related to those performance claims and warranties, and if a\n" +
        "court requires any other Contributor to pay any damages as a result, the\n" +
        "Commercial Contributor must pay those damages.\n" +
        "\n" +
        "5. NO WARRANTY\n" +
        "\n" +
        "EXCEPT AS EXPRESSLY SET FORTH IN THIS AGREEMENT, THE PROGRAM IS PROVIDED ON AN\n" +
        "\"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, EITHER EXPRESS OR\n" +
        "IMPLIED INCLUDING, WITHOUT LIMITATION, ANY WARRANTIES OR CONDITIONS OF TITLE,\n" +
        "NON-INFRINGEMENT, MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Each\n" +
        "Recipient is solely responsible for determining the appropriateness of using\n" +
        "and distributing the Program and assumes all risks associated with its\n" +
        "exercise of rights under this Agreement , including but not limited to the\n" +
        "risks and costs of program errors, compliance with applicable laws, damage to\n" +
        "or loss of data, programs or equipment, and unavailability or interruption of\n" +
        "operations.\n" +
        "\n" +
        "6. DISCLAIMER OF LIABILITY\n" +
        "\n" +
        "EXCEPT AS EXPRESSLY SET FORTH IN THIS AGREEMENT, NEITHER RECIPIENT NOR ANY\n" +
        "CONTRIBUTORS SHALL HAVE ANY LIABILITY FOR ANY DIRECT, INDIRECT, INCIDENTAL,\n" +
        "SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING WITHOUT LIMITATION\n" +
        "LOST PROFITS), HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN\n" +
        "CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)\n" +
        "ARISING IN ANY WAY OUT OF THE USE OR DISTRIBUTION OF THE PROGRAM OR THE\n" +
        "EXERCISE OF ANY RIGHTS GRANTED HEREUNDER, EVEN IF ADVISED OF THE POSSIBILITY\n" +
        "OF SUCH DAMAGES.\n" +
        "\n" +
        "7. GENERAL\n" +
        "\n" +
        "If any provision of this Agreement is invalid or unenforceable under\n" +
        "applicable law, it shall not affect the validity or enforceability of the\n" +
        "remainder of the terms of this Agreement, and without further action by the\n" +
        "parties hereto, such provision shall be reformed to the minimum extent\n" +
        "necessary to make such provision valid and enforceable.\n" +
        "\n" +
        "If Recipient institutes patent litigation against any entity (including a\n" +
        "cross-claim or counterclaim in a lawsuit) alleging that the Program itself\n" +
        "(excluding combinations of the Program with other software or hardware)\n" +
        "infringes such Recipient's patent(s), then such Recipient's rights granted\n" +
        "under Section 2(b) shall terminate as of the date such litigation is filed.\n" +
        "\n" +
        "All Recipient's rights under this Agreement shall terminate if it fails to\n" +
        "comply with any of the material terms or conditions of this Agreement and does\n" +
        "not cure such failure in a reasonable period of time after becoming aware of\n" +
        "such noncompliance. If all Recipient's rights under this Agreement terminate,\n" +
        "Recipient agrees to cease use and distribution of the Program as soon as\n" +
        "reasonably practicable. However, Recipient's obligations under this Agreement\n" +
        "and any licenses granted by Recipient relating to the Program shall continue\n" +
        "and survive.\n" +
        "\n" +
        "Everyone is permitted to copy and distribute copies of this Agreement, but in\n" +
        "order to avoid inconsistency the Agreement is copyrighted and may only be\n" +
        "modified in the following manner. The Agreement Steward reserves the right to\n" +
        "publish new versions (including revisions) of this Agreement from time to\n" +
        "time. No one other than the Agreement Steward has the right to modify this\n" +
        "Agreement. The Eclipse Foundation is the initial Agreement Steward. The\n" +
        "Eclipse Foundation may assign the responsibility to serve as the Agreement\n" +
        "Steward to a suitable separate entity. Each new version of the Agreement will\n" +
        "be given a distinguishing version number. The Program (including\n" +
        "Contributions) may always be distributed subject to the version of the\n" +
        "Agreement under which it was received. In addition, after a new version of the\n" +
        "Agreement is published, Contributor may elect to distribute the Program\n" +
        "(including its Contributions) under the new version. Except as expressly\n" +
        "stated in Sections 2(a) and 2(b) above, Recipient receives no rights or\n" +
        "licenses to the intellectual property of any Contributor under this Agreement,\n" +
        "whether expressly, by implication, estoppel or otherwise. All rights in the\n" +
        "Program not expressly granted under this Agreement are reserved.\n" +
        "\n" +
        "This Agreement is governed by the laws of the State of New York and the\n" +
        "intellectual property laws of the United States of America. No party to this\n" +
        "Agreement will bring a legal action under this Agreement more than one year\n" +
        "after the cause of action arose. Each party waives its rights to a jury trial in\n" +
        "any resulting litigation.\n"
    ),

    SIMPLE_BSD("simple-bsd",
        "Copyright (c) %s, %s\n" +
        "All rights reserved.\n" +
        "\n" +
        "Redistribution and use in url and binary forms, with or without\n" +
        "modification, are permitted provided that the following conditions are met:\n" +
        "\n" +
        "* Redistributions of url code must retain the above copyright notice, this\n" +
        "  list of conditions and the following disclaimer.\n" +
        "\n" +
        "* Redistributions in binary form must reproduce the above copyright notice,\n" +
        "  this list of conditions and the following disclaimer in the documentation\n" +
        "  and/or other materials provided with the distribution.\n" +
        "\n" +
        "THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS \"AS IS\"\n" +
        "AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE\n" +
        "IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE\n" +
        "DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE\n" +
        "FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL\n" +
        "DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR\n" +
        "SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER\n" +
        "CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,\n" +
        "OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE\n" +
        "OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.\n"
    ),

    NEW_BSD("new-bsd",
        "Copyright (c) %s, %s\n" +
        "All rights reserved.\n" +
        "\n" +
        "Redistribution and use in url and binary forms, with or without\n" +
        "modification, are permitted provided that the following conditions are met:\n" +
        "\n" +
        "* Redistributions of url code must retain the above copyright notice, this\n" +
        "  list of conditions and the following disclaimer.\n" +
        "\n" +
        "* Redistributions in binary form must reproduce the above copyright notice,\n" +
        "  this list of conditions and the following disclaimer in the documentation\n" +
        "  and/or other materials provided with the distribution.\n" +
        "\n" +
        "* Neither the name of [project] nor the names of its\n" +
        "  contributors may be used to endorse or promote products derived from\n" +
        "  this software without specific prior written permission.\n" +
        "\n" +
        "THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS \"AS IS\"\n" +
        "AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE\n" +
        "IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE\n" +
        "DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE\n" +
        "FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL\n" +
        "DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR\n" +
        "SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER\n" +
        "CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,\n" +
        "OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE\n" +
        "OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE."
    ),

    ISC_LICENSE("isc-license",
        "Copyright (c) %s, %s %s\n" +
        "\n" +
        "Permission to use, copy, modify, and/or distribute this software for any\n" +
        "purpose with or without fee is hereby granted, provided that the above\n" +
        "copyright notice and this permission notice appear in all copies.\n" +
        "\n" +
        "THE SOFTWARE IS PROVIDED \"AS IS\" AND THE AUTHOR DISCLAIMS ALL WARRANTIES\n" +
        "WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF\n" +
        "MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR\n" +
        "ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES\n" +
        "WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN\n" +
        "ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF\n" +
        "OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE."
    ),

    LGPL_V2_1("lgpl-v2.1",
        "%s\n" +
        "Copyright (C) %s %s\n" +
        "\n" +
        "This library is free software; you can redistribute it and/or\n" +
        "modify it under the terms of the GNU Lesser General Public\n" +
        "License as published by the Free Software Foundation; either\n" +
        "version 2.1 of the License, or (at your option) any later version.\n" +
        "\n" +
        "This library is distributed in the hope that it will be useful,\n" +
        "but WITHOUT ANY WARRANTY; without even the implied warranty of\n" +
        "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU\n" +
        "Lesser General Public License for more details.\n" +
        "\n" +
        "You should have received a copy of the GNU Lesser General Public\n" +
        "License along with this library; if not, write to the Free Software\n" +
        "Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301\n" +
        "USA"
    ),

    LGPL_V3("lgpl-v3",
        "                   GNU LESSER GENERAL PUBLIC LICENSE\n" +
        "                       Version 3, 29 June 2007\n" +
        "\n" +
        " Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>\n" +
        " Everyone is permitted to copy and distribute verbatim copies\n" +
        " of this license document, but changing it is not allowed.\n" +
        "\n" +
        "\n" +
        "  This version of the GNU Lesser General Public License incorporates\n" +
        "the terms and conditions of version 3 of the GNU General Public\n" +
        "License, supplemented by the additional permissions listed below.\n" +
        "\n" +
        "  0. Additional Definitions.\n" +
        "\n" +
        "  As used herein, \"this License\" refers to version 3 of the GNU Lesser\n" +
        "General Public License, and the \"GNU GPL\" refers to version 3 of the GNU\n" +
        "General Public License.\n" +
        "\n" +
        "  \"The Library\" refers to a covered work governed by this License,\n" +
        "other than an Application or a Combined Work as defined below.\n" +
        "\n" +
        "  An \"Application\" is any work that makes use of an interface provided\n" +
        "by the Library, but which is not otherwise based on the Library.\n" +
        "Defining a subclass of a class defined by the Library is deemed a mode\n" +
        "of using an interface provided by the Library.\n" +
        "\n" +
        "  A \"Combined Work\" is a work produced by combining or linking an\n" +
        "Application with the Library.  The particular version of the Library\n" +
        "with which the Combined Work was made is also called the \"Linked\n" +
        "Version\".\n" +
        "\n" +
        "  The \"Minimal Corresponding Source\" for a Combined Work means the\n" +
        "Corresponding Source for the Combined Work, excluding any url code\n" +
        "for portions of the Combined Work that, considered in isolation, are\n" +
        "based on the Application, and not on the Linked Version.\n" +
        "\n" +
        "  The \"Corresponding Application Code\" for a Combined Work means the\n" +
        "object code and/or url code for the Application, including any data\n" +
        "and utility programs needed for reproducing the Combined Work from the\n" +
        "Application, but excluding the System Libraries of the Combined Work.\n" +
        "\n" +
        "  1. Exception to Section 3 of the GNU GPL.\n" +
        "\n" +
        "  You may convey a covered work under sections 3 and 4 of this License\n" +
        "without being bound by section 3 of the GNU GPL.\n" +
        "\n" +
        "  2. Conveying Modified Versions.\n" +
        "\n" +
        "  If you modify a copy of the Library, and, in your modifications, a\n" +
        "facility refers to a function or data to be supplied by an Application\n" +
        "that uses the facility (other than as an argument passed when the\n" +
        "facility is invoked), then you may convey a copy of the modified\n" +
        "version:\n" +
        "\n" +
        "   a) under this License, provided that you make a good faith effort to\n" +
        "   ensure that, in the event an Application does not supply the\n" +
        "   function or data, the facility still operates, and performs\n" +
        "   whatever part of its purpose remains meaningful, or\n" +
        "\n" +
        "   b) under the GNU GPL, with none of the additional permissions of\n" +
        "   this License applicable to that copy.\n" +
        "\n" +
        "  3. Object Code Incorporating Material from Library Header Files.\n" +
        "\n" +
        "  The object code form of an Application may incorporate material from\n" +
        "a header file that is part of the Library.  You may convey such object\n" +
        "code under terms of your choice, provided that, if the incorporated\n" +
        "material is not limited to numerical parameters, data structure\n" +
        "layouts and accessors, or small macros, inline functions and templates\n" +
        "(ten or fewer lines in length), you do both of the following:\n" +
        "\n" +
        "   a) Give prominent notice with each copy of the object code that the\n" +
        "   Library is used in it and that the Library and its use are\n" +
        "   covered by this License.\n" +
        "\n" +
        "   b) Accompany the object code with a copy of the GNU GPL and this license\n" +
        "   document.\n" +
        "\n" +
        "  4. Combined Works.\n" +
        "\n" +
        "  You may convey a Combined Work under terms of your choice that,\n" +
        "taken together, effectively do not restrict modification of the\n" +
        "portions of the Library contained in the Combined Work and reverse\n" +
        "engineering for debugging such modifications, if you also do each of\n" +
        "the following:\n" +
        "\n" +
        "   a) Give prominent notice with each copy of the Combined Work that\n" +
        "   the Library is used in it and that the Library and its use are\n" +
        "   covered by this License.\n" +
        "\n" +
        "   b) Accompany the Combined Work with a copy of the GNU GPL and this license\n" +
        "   document.\n" +
        "\n" +
        "   c) For a Combined Work that displays copyright notices during\n" +
        "   execution, include the copyright notice for the Library among\n" +
        "   these notices, as well as a reference directing the user to the\n" +
        "   copies of the GNU GPL and this license document.\n" +
        "\n" +
        "   d) Do one of the following:\n" +
        "\n" +
        "       0) Convey the Minimal Corresponding Source under the terms of this\n" +
        "       License, and the Corresponding Application Code in a form\n" +
        "       suitable for, and under terms that permit, the user to\n" +
        "       recombine or relink the Application with a modified version of\n" +
        "       the Linked Version to produce a modified Combined Work, in the\n" +
        "       manner specified by section 6 of the GNU GPL for conveying\n" +
        "       Corresponding Source.\n" +
        "\n" +
        "       1) Use a suitable shared library mechanism for linking with the\n" +
        "       Library.  A suitable mechanism is one that (a) uses at run time\n" +
        "       a copy of the Library already present on the user's computer\n" +
        "       system, and (b) will operate properly with a modified version\n" +
        "       of the Library that is interface-compatible with the Linked\n" +
        "       Version.\n" +
        "\n" +
        "   e) Provide Installation Information, but only if you would otherwise\n" +
        "   be required to provide such information under section 6 of the\n" +
        "   GNU GPL, and only to the extent that such information is\n" +
        "   necessary to install and execute a modified version of the\n" +
        "   Combined Work produced by recombining or relinking the\n" +
        "   Application with a modified version of the Linked Version. (If\n" +
        "   you use option 4d0, the Installation Information must accompany\n" +
        "   the Minimal Corresponding Source and Corresponding Application\n" +
        "   Code. If you use option 4d1, you must provide the Installation\n" +
        "   Information in the manner specified by section 6 of the GNU GPL\n" +
        "   for conveying Corresponding Source.)\n" +
        "\n" +
        "  5. Combined Libraries.\n" +
        "\n" +
        "  You may place library facilities that are a work based on the\n" +
        "Library side by side in a single library together with other library\n" +
        "facilities that are not Applications and are not covered by this\n" +
        "License, and convey such a combined library under terms of your\n" +
        "choice, if you do both of the following:\n" +
        "\n" +
        "   a) Accompany the combined library with a copy of the same work based\n" +
        "   on the Library, uncombined with any other library facilities,\n" +
        "   conveyed under the terms of this License.\n" +
        "\n" +
        "   b) Give prominent notice with the combined library that part of it\n" +
        "   is a work based on the Library, and explaining where to find the\n" +
        "   accompanying uncombined form of the same work.\n" +
        "\n" +
        "  6. Revised Versions of the GNU Lesser General Public License.\n" +
        "\n" +
        "  The Free Software Foundation may publish revised and/or new versions\n" +
        "of the GNU Lesser General Public License from time to time. Such new\n" +
        "versions will be similar in spirit to the present version, but may\n" +
        "differ in detail to address new problems or concerns.\n" +
        "\n" +
        "  Each version is given a distinguishing version number. If the\n" +
        "Library as you received it specifies that a certain numbered version\n" +
        "of the GNU Lesser General Public License \"or any later version\"\n" +
        "applies to it, you have the option of following the terms and\n" +
        "conditions either of that published version or of any later version\n" +
        "published by the Free Software Foundation. If the Library as you\n" +
        "received it does not specify a version number of the GNU Lesser\n" +
        "General Public License, you may choose any version of the GNU Lesser\n" +
        "General Public License ever published by the Free Software Foundation.\n" +
        "\n" +
        "  If the Library as you received it specifies that a proxy can decide\n" +
        "whether future versions of the GNU Lesser General Public License shall\n" +
        "apply, that proxy's public statement of acceptance of any version is\n" +
        "permanent authorization for you to choose that version for the\n" +
        "Library.\n"
    ),

    MOZILLA("mozilla",
        "This Source Code Form is subject to the\n" +
        "terms of the Mozilla Public License, v.\n" +
        "2.0. If a copy of the MPL was not\n" +
        "distributed with this file, You can\n" +
        "obtain one at\n" +
        "http://mozilla.org/MPL/2.0/."
    ),

    UNLICENSE("unlicense",
        "This is free and unencumbered software released into the public domain.\n" +
        "\n" +
        "Anyone is free to copy, modify, publish, use, compile, sell, or\n" +
        "distribute this software, either in url code form or as a compiled\n" +
        "binary, for any purpose, commercial or non-commercial, and by any\n" +
        "means.\n" +
        "\n" +
        "In jurisdictions that recognize copyright laws, the author or authors\n" +
        "of this software dedicate any and all copyright interest in the\n" +
        "software to the public domain. We make this dedication for the benefit\n" +
        "of the public at large and to the detriment of our heirs and\n" +
        "successors. We intend this dedication to be an overt act of\n" +
        "relinquishment in perpetuity of all present and future rights to this\n" +
        "software under copyright law.\n" +
        "\n" +
        "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND,\n" +
        "EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF\n" +
        "MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.\n" +
        "IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR\n" +
        "OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,\n" +
        "ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR\n" +
        "OTHER DEALINGS IN THE SOFTWARE.\n" +
        "\n" +
        "For more information, please refer to <http://unlicense.org>\n"
    ),

    NONE("", "");

    private final String mKey;
    private final String mLicenseText;

    /**
     * Constructor
     * @param license       the license text with format
     */
    License(String key,String license){
        mKey = key;
        mLicenseText = license;
    }

    /**
     * Get the license text formated with data
     *
     * @param description       a one line description of the project, if applicable
     * @param year              the year of hte license/project, if applicable
     * @param name              the author of the code/project, if applicable
     * @param email             the email of the code/project, if applicable
     * @return                  the formated license text string
     */
    public String getLicense(String description, String year, String name, String email){
        try {
            return String.format(mLicenseText, description, year, name, email);
        }catch (IllegalFormatException e){
            try{
                return String.format(mLicenseText, year, name, email);
            }catch (IllegalFormatException e1){
                try{
                    return String.format(mLicenseText, year, name);
                }catch (IllegalFormatException e2){
                    return mLicenseText;
                }
            }
        }
    }

    /**
     * Get a license from it's specified KEY
     *
     * @param key       the license key, i.e. 'apache', 'mit', 'gpl-v3', etc...
     * @return          the License ENUM
     */
    public static License fromKey(String key){
        for(int i=0; i<License.values().length; i++){
            License license = License.values()[i];
            if(license.mKey.equalsIgnoreCase(key)){
                return license;
            }
        }
        return null;
    }

}
